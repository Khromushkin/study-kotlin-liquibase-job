package ru.qiwi.study.test.svc.db.config

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.qiwi.study.test_service.name.db.config.DatabaseProperties
import javax.sql.DataSource

@Configuration
class DatabaseConfiguration {

    @Bean
    fun dataSource(databaseProperties: DatabaseProperties): DataSource {
        return DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver")
            .url(databaseProperties.jdbcUrl)
            .username(databaseProperties.user)
            .password(databaseProperties.password)
            .build()
    }
}
