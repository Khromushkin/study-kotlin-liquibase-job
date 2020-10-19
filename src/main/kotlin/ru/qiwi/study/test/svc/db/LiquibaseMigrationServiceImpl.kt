package ru.qiwi.study.test.svc.db

import liquibase.Liquibase
import liquibase.integration.spring.SpringLiquibase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.Properties
import javax.annotation.PostConstruct
import javax.sql.DataSource

//@Service
class LiquibaseMigrationServiceImpl(
    private val _dataSource: DataSource
) : SpringLiquibase() {
    private lateinit var releaseTag: String
    private lateinit var liquibase: Liquibase

    init {
        changeLog = "db/changelog-master.yaml"
        val properties = Properties()
        properties.load(javaClass.classLoader.getResourceAsStream("project.properties"))
        releaseTag = properties.getProperty("artifactId") + "_" + properties.getProperty("version")
    }

    @PostConstruct
    private fun initializeLiquibase() {
        liquibase = createLiquibase(_dataSource.connection)
        if (!liquibase.tagExists(initialTag)) {
            liquibase.tag(initialTag)
        }
    }

    fun runMigration() {
        logger.info("Database migration to $releaseTag start")
        if (liquibase.tagExists(releaseTag)) {
            runRollback(releaseTag)
        } else {
            runUpdate()
            liquibase.tag(releaseTag)
        }
    }

    fun runUpdate() {
        logger.info("Database migration update start")
        liquibase.update(System.getenv("ENVIRONMENT_PROFILE_NAME"))
    }

    fun runRollback(tag: String = initialTag) {
        logger.info("Database migration rollback to $tag start")
        liquibase.rollback(tag, allContexts)
    }

    @Override
    override fun afterPropertiesSet() {
    }

    companion object {
        private val logger = LoggerFactory.getLogger(LiquibaseMigrationServiceImpl::class.java)
        private const val initialTag = "initial"
        private const val allContexts = "testing, production, dev"
    }
}