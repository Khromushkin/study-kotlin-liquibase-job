package ru.qiwi.study.test.svc.db.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "database")
class DatabaseProperties {
    lateinit var jdbcUrl: String
    lateinit var user: String
    lateinit var password: String
}