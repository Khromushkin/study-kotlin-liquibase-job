package Products_Study_TestSvcDb

import ru.qiwi.devops.teamcity.*
import ru.qiwi.devops.teamcity.buildTypes.*
import ru.qiwi.devops.teamcity.extensions.*

object Project : QiwiCommonProject({
    name = "study-kotlin-liquibase"
    productSpace = ProductSpace.Study
    description = "test service db"

    git {
        project = "study/kotlin-liquibase"
        server = GitServer.GERRIT
    }
    
    +GerritVerify() triggersBy commitInChangesBranches()


    pipeline {
        +CommitBuild() aka "commitBuild" triggersBy commit()
        phase {
            +DeployKubernetes(Environment.TESTING) aka "deployTesting"
            +SecurityCheck()
        }
        +StartWorks() aka "startWorks"
        +DeployKubernetes(Environment.PRODUCTION_M1)
        +FinishWorks() triggersBy task("startWorks")
    }

    +Release() triggersBy task("deployTesting")
})