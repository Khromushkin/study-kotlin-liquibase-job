package ru.qiwi.study.test.svc.db

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import ru.qiwi.common.boot.QiwiSpringBootApplication
import ru.qiwi.study.test_service.name.db.config.DatabaseProperties
import kotlin.system.exitProcess

@SpringBootApplication
@EnableConfigurationProperties(
    DatabaseProperties::class
)
class StudyTestSvcDbApplication

fun main(args: Array<String>) {
    QiwiSpringBootApplication
        .run(StudyTestSvcDbApplication::class.java, *args)
        .getBean(LiquibaseMigrationServiceImpl::class.java)
        .runMigration()
    exitProcess(0)
}
