package itmo.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Simple Migration Tests")
public class SimpleMigrationTest {

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
    @DisplayName("Should execute PostgreSQL migration without errors")
    void testPostgreSQLMigration() throws Exception {
        // Читаем PostgreSQL миграцию
        String migration = new String(Files.readAllBytes(
            Paths.get("src/main/resources/db/V1__init.sql")
        ));

        // Выполняем миграцию
        try (Statement stmt = connection.createStatement()) {
            // Заменяем PostgreSQL-специфичный синтаксис на H2-совместимый
            String h2Migration = migration
                .replace("serial", "BIGINT AUTO_INCREMENT")
                .replace("double precision", "DOUBLE")
                .replace("bigint", "BIGINT")
                .replace("varchar(255)", "VARCHAR(255)")
                .replace("timestamp", "TIMESTAMP");
            
            stmt.execute(h2Migration);
        }

        // Проверяем, что миграция выполнена без ошибок
        assertDoesNotThrow(() -> {
            try (Statement stmt = connection.createStatement()) {
                stmt.executeQuery("SELECT COUNT(*) FROM locations");
                stmt.executeQuery("SELECT COUNT(*) FROM routes");
            }
        }, "Migration should execute without errors");
    }

    @Test
    @DisplayName("Should execute H2 migration without errors")
    void testH2Migration() throws Exception {
        // Читаем H2 миграцию
        String migration = new String(Files.readAllBytes(
            Paths.get("src/main/resources/db/V1__init_h2.sql")
        ));

        // Выполняем миграцию
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(migration);
        }

        // Проверяем, что миграция выполнена без ошибок
        assertDoesNotThrow(() -> {
            try (Statement stmt = connection.createStatement()) {
                stmt.executeQuery("SELECT COUNT(*) FROM locations");
                stmt.executeQuery("SELECT COUNT(*) FROM routes");
            }
        }, "H2 migration should execute without errors");
    }

    @Test
    @DisplayName("Should insert and query data successfully")
    void testDataOperations() throws Exception {
        // Выполняем H2 миграцию
        String migration = new String(Files.readAllBytes(
            Paths.get("src/main/resources/db/V1__init_h2.sql")
        ));

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(migration);
        }

        // Вставляем тестовые данные
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO locations (x, y, name) VALUES (100, 25.5, 'Test Location')");
            stmt.execute("INSERT INTO locations (x, y, name) VALUES (200, 30.2, 'Another Location')");
            
            stmt.execute("INSERT INTO routes (name, coordinate_x, coordinate_y, creation_date, from_id, to_id, distance, rating) " +
                "VALUES ('Test Route', 150.0, 45.0, CURRENT_TIMESTAMP, 1, 2, 635.0, 4.5)");
        }

        // Проверяем, что данные вставлены
        try (Statement stmt = connection.createStatement()) {
            var locationsResult = stmt.executeQuery("SELECT COUNT(*) as count FROM locations");
            assertTrue(locationsResult.next());
            assertEquals(2, locationsResult.getInt("count"));

            var routesResult = stmt.executeQuery("SELECT COUNT(*) as count FROM routes");
            assertTrue(routesResult.next());
            assertEquals(1, routesResult.getInt("count"));
        }
    }

    @Test
    @DisplayName("Should enforce coordinate_y constraint")
    void testCoordinateYConstraint() throws Exception {
        // Выполняем H2 миграцию
        String migration = new String(Files.readAllBytes(
            Paths.get("src/main/resources/db/V1__init_h2.sql")
        ));

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(migration);
        }

        // Создаем location
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO locations (x, y, name) VALUES (1, 1.0, 'Test Location')");
        }

        // Пытаемся вставить route с coordinate_y > 49
        try (Statement stmt = connection.createStatement()) {
            assertThrows(SQLException.class, () -> {
                stmt.execute("INSERT INTO routes (name, coordinate_x, coordinate_y, creation_date, from_id, to_id, distance, rating) " +
                    "VALUES ('Test Route', 1.0, 50.0, CURRENT_TIMESTAMP, 1, 1, 100.0, 5.0)");
            }, "Should fail when coordinate_y > 49");
        }
    }
}

