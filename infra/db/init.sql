CREATE ROLE test_service_name_user WITH LOGIN PASSWORD 'secretpassword' NOSUPERUSER INHERIT CREATEDB CREATEROLE NOREPLICATION;
CREATE ROLE test_service_name WITH NOLOGIN;

create schema test_service_name AUTHORIZATION test_service_name_user;
GRANT ALL ON SCHEMA test_service_name to test_service_name_user;
grant usage on schema test_service_name to test_service_name_user;
ALTER ROLE test_service_name_user SET search_path TO test_service_name,pg_catalog,public;

CREATE USER test_service_name_app WITH LOGIN PASSWORD 'secretpassword' NOSUPERUSER INHERIT NOREPLICATION;
GRANT USAGE ON SCHEMA test_service_name to test_service_name_app;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";