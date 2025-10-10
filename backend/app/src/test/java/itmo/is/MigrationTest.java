package itmo.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Migration Tests")
public class MigrationTest {

    private Connection connection;
    private static final String TEST_DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String TEST_DB_USER = "sa";
    private static final String TEST_DB_PASSWORD = "";

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(TEST_DB_URL, TEST_DB_USER, TEST_DB_PASSWORD);
        // Очищаем базу данных перед каждым тестом
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS routes");
            stmt.execute("DROP TABLE IF EXISTS locations");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    @DisplayName("Should create tables according to migration")
    void testMigrationCreatesTables() throws Exception {
        // Читаем миграцию
        String migration = new String(Files.readAllBytes(
            Paths.get("src/main/resources/db/V1__init_h2.sql")
        ));

        // Выполняем миграцию
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(migration);
        }

        // Проверяем, что таблицы созданы
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'"
            );
            
            boolean locationsFound = false;
            boolean routesFound = false;
            
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                if ("LOCATIONS".equals(tableName)) {
                    locationsFound = true;
                } else if ("ROUTES".equals(tableName)) {
                    routesFound = true;
                }
            }
            
            assertTrue(locationsFound, "Table 'locations' should be created");
            assertTrue(routesFound, "Table 'routes' should be created");
        }
    }

    @Test
    @DisplayName("Should create indexes according to migration")
    void testMigrationCreatesIndexes() throws Exception {
        // Читаем и выполняем миграцию
        String migration = new String(Files.readAllBytes(
            Paths.get("src/main/resources/db/V1__init_h2.sql")
        ));

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(migration);
        }

        // Проверяем индексы
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(
                "SELECT INDEX_NAME FROM INFORMATION_SCHEMA.INDEXES WHERE TABLE_SCHEMA = 'PUBLIC'"
            );
            
            boolean fromIndexFound = false;
            boolean toIndexFound = false;
            
            while (rs.next()) {
                String indexName = rs.getString("INDEX_NAME");
                if (indexName.contains("FROM_LOCATION") || indexName.contains("FROM_ID")) {
                    fromIndexFound = true;
                } else if (indexName.contains("TO_LOCATION") || indexName.contains("TO_ID")) {
                    toIndexFound = true;
                }
            }
            
            assertTrue(fromIndexFound, "Index on from_id should be created");
            assertTrue(toIndexFound, "Index on to_id should be created");
        }
    }

    @Test
    @DisplayName("Should enforce foreign key constraints")
    void testForeignKeyConstraints() throws Exception {
        // Выполняем миграцию
        String migration = new String(Files.readAllBytes(
            Paths.get("src/main/resources/db/V1__init_h2.sql")
        ));

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(migration);
        }

        // Пытаемся вставить route без существующих locations
        try (Statement stmt = connection.createStatement()) {
            assertThrows(SQLException.class, () -> {
                stmt.execute(
                    "INSERT INTO routes (name, coordinate_x, coordinate_y, creation_date, from_id, to_id, distance, rating) " +
                    "VALUES ('Test Route', 1.0, 2.0, CURRENT_TIMESTAMP, 999, 998, 100.0, 5.0)"
                );
            }, "Should fail when inserting route with non-existent location references");
        }
    }

    @Test
    @DisplayName("Should enforce coordinate_y constraint")
    void testCoordinateYConstraint() throws Exception {
        // Выполняем миграцию
        String migration = new String(Files.readAllBytes(
            Paths.get("src/main/resources/db/V1__init_h2.sql")
        ));

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(migration);
        }

        // Сначала создаем locations
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO locations (x, y, name) VALUES (1, 1.0, 'Test Location')");
        }

        // Пытаемся вставить route с coordinate_y > 49
        try (Statement stmt = connection.createStatement()) {
            assertThrows(SQLException.class, () -> {
                stmt.execute(
                    "INSERT INTO routes (name, coordinate_x, coordinate_y, creation_date, from_id, to_id, distance, rating) " +
                    "VALUES ('Test Route', 1.0, 50.0, CURRENT_TIMESTAMP, 1, 1, 100.0, 5.0)"
                );
            }, "Should fail when coordinate_y > 49");
        }
    }
}
