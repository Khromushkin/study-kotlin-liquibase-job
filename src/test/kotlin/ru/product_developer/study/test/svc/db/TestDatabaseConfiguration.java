package ru.product_developer.study.test.svc.db;

import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.GenericContainer;

import javax.sql.DataSource;

@Configuration
public class TestDatabaseConfiguration {

    @Bean
    GenericContainer postgreSQLContainer() {
        GenericContainer container = new GenericContainer("kotlin-liquibase")
                .withExposedPorts(5432);

        container.start();
        return container;
    }

    @Bean
    @LiquibaseDataSource
    public DataSource liquibaseDataSource(final GenericContainer postgreSQLContainer) {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:"
                        + postgreSQLContainer.getMappedPort(5432)
                        + "/postgres")
                .username("test_service_name_user")
                .password("secretpassword")
                .build();
    }
}
