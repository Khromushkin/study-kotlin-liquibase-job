build_db:
	docker build -t kotlin-liquibase infra/db/.

stop_db:
	docker container rm -f kotlin-liquibase || true

run_db:
	make stop_db
	make build_db
	docker run -d --name kotlin-liquibase -p 5433:5432 kotlin-liquibase