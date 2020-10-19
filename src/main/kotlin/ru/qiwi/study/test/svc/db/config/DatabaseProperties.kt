package ru.qiwi.study.test_service.name.db.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
@ConfigurationProperties(prefix = "database")
class DatabaseProperties {

    @NotBlank
    lateinit var jdbcUrl: String
    @NotBlank
    lateinit var user: String
    @NotBlank
    lateinit var password: String
}