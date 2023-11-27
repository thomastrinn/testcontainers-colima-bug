package hu.thomas.testcontainerscolimabug;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class ExampleTest {

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) throws InterruptedException {
        if (Boolean.parseBoolean(System.getProperty("wait-a-bit", "false"))) {
            Thread.sleep(2_000L);
        }
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Test
    void test() {

    }
}
