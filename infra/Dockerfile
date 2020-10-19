FROM dcr.domaindebian-jessie-jre:v10

ENV JAVA_OPTS='' \
    JAVA_ARGS='' \
    JAVA_MAIN_CLASS='' \
    SPRING_CONFIG_LOCATION_PARAMETER_NAME='spring.config.additional-location' \
    JAVA_APP_JAR='study-kotlin-liquibase.jar'

COPY target/study-kotlin-liquibase.jar $APP_DIR/
