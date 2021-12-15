# study-kotlin-liquibase-job #


#### What is this repository for? ####

This software is for Engineering practices demonstration:

* Database versioning by using Liquibase;
* SpringLiquibase integration;
* Migration tests by using TestContainers;
* Database migrations deployment by using Kubernetes Job

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

#### Built With ####

* [Spring Boot](https://projects.spring.io/spring-boot/) - Framework for easy bootstrapping and developing new Spring applications.
* [Spring Web MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html) - Web framework built on the Servlet API
* [Maven](https://maven.apache.org/) - Dependency Management
* [CheckStyle](http://checkstyle.sourceforge.net/) - Checking code style with [Google Checks](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml)
* [FindBugs](http://findbugs.sourceforge.net/) - Static code analysis for possible bugs


#### TODO ####
* Add Travis CI or GH Actions pipeline