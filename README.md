# study-kotlin-liquibase #


#### What is this repository for? ####

This software is for SpringLiquibase usage demonstration with Kotlin.

#### How do I get set up? ####

You can import *pom.xml* into IntelliJIdea or use command-line interface.
Running application can be done with the following command:
```
mvn spring-boot:run
```

Or you can create a full package and launch it from any place:
```
mvn clean package && java -jar target/study-kotlin-liquibase.jar
```


#### Verify, build and deploy ####

You can verify project by running
```
make verify
```

If you want to build a Docker image with this project, you can run this:
```
make build
```

or to build an image and push it to the Docker registry (default is dcr.domain:
```
make DOCKER_REGISTRY=dcr.domainVERSION_SUFFIX='-12345' build_push
```

If you need to deploy image to the Kubernetes cluster, use the following command:
```
make IMAGE=dcr.domainstudy-kotlin-liquibase:1.0.0-12345 deploy
```


#### Built With ####

* [Spring Boot](https://projects.spring.io/spring-boot/) - Framework for easy bootstrapping and developing new Spring applications.
* [Spring Web MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html) - Web framework built on the Servlet API
* [Maven](https://maven.apache.org/) - Dependency Management
* [CheckStyle](http://checkstyle.sourceforge.net/) - Checking code style with [Google Checks](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml)
* [FindBugs](http://findbugs.sourceforge.net/) - Static code analysis for possible bugs


#### TODO ####
* Remove qiwi-common-platform dependency and add Travis CI integration