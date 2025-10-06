/*
 * Spring Boot Application Test
 */
package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import itmo.is.App;

@SpringBootTest(classes = App.class)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class AppTest {
    @Test void contextLoads() {
        // This test verifies that the Spring Boot application context loads successfully
    }
}
