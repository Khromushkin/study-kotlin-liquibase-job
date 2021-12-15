package ru.product_developer.study.test.svc.db

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(
    classes = [
        TestDatabaseConfiguration::class,
        LiquibaseMigrationServiceImpl::class
    ]
)
class StudyTestSvcDbApplicationTests {
    @Autowired
    private lateinit var databaseMigration: LiquibaseMigrationServiceImpl

    @Test
    fun contextLoads() {
    }

    @Test
    fun databaseMigrationRuns() {
        databaseMigration.runUpdate()
    }

    @Test
    fun databaseMigrationRollbacks() {
        databaseMigration.runUpdate()
        databaseMigration.runRollback()
    }
}
