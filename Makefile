SHELL := $(shell which bash)
.DEFAULT_GOAL := help

help: ## This help.
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}' $(MAKEFILE_LIST)

MVN_EXEC=$(shell which mvn || echo ${M2_HOME}'/bin/mvn')
DOCKER_EXEC=$(shell which docker)
KUBECTL_EXEC=$(shell which kubectl)
GIT_EXEC=$(shell which git)
ENVSUBST_EXEC=$(shell which envsubst)

## General setting
DOCKER_REGISTRY?=dcr.domainINFRA_DIRECTORY?=./infra/
SOURCES_DIRECTORY?=./
DOCKER_OPTIONS?=
KUBECTL_OPTIONS?=

MVN_OPTIONS?=-f $(SOURCES_DIRECTORY)pom.xml

DOCKERFILE_VERIFIER_NAME?=hadolint
DOCKERFILE_VERIFIER_VERSION?=latest
DOCKERFILE_VERIFIER_IMAGE?=$(DOCKER_REGISTRY)/$(DOCKERFILE_VERIFIER_NAME):$(DOCKERFILE_VERIFIER_VERSION)

APP_NAME=study-kotlin-liquibase
APP_SPACE=study
VERSION_SUFFIX?=-$(shell echo $$USER-$$(date +'%Y%m%d%H%M%S'))

env:
	$(eval VERSION=$(shell $(MVN_EXEC) $(MVN_OPTIONS) -q -Dexec.executable="echo" -Dexec.args='$${project.version}$(VERSION_SUFFIX)' --non-recursive org.codehaus.mojo:exec-maven-plugin:exec))
	$(eval IMAGE_TAG=$(DOCKER_REGISTRY)/$(APP_NAME):$(VERSION))
	$(eval IMAGE_TAG_LATEST=$(DOCKER_REGISTRY)/$(APP_NAME):latest)

app_name: ## get application name
	@echo $(APP_NAME)

app_version: env ## get application version
	@echo $(VERSION)

image_tag: env ## get image tag
	@echo $(IMAGE_TAG)

increment_version: env ## increment app version
	$(eval NEW_VERSION=$(shell echo -n $(VERSION) | awk -F. '{print $$1+0"."$$2+1".0"}'))
	$(MVN_EXEC) $(MVN_OPTIONS) versions:set -DnewVersion=$(NEW_VERSION) -DgenerateBackupPoms=false -DprocessPlugins=false

verify: ## verify project
	make build_db
	$(MVN_EXEC) $(MVN_OPTIONS) verify || { echo 'verify failed' ; exit 1; }
	sudo $(DOCKER_EXEC) run --rm -i $(DOCKERFILE_VERIFIER_IMAGE) < $(INFRA_DIRECTORY)Dockerfile
	$(MVN_EXEC) -f .teamcity/pom.xml org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate || { echo 'verify failed' ; exit 1; }

build: env ## build application
	make build_db
	$(MVN_EXEC) $(MVN_OPTIONS) package
	sudo $(DOCKER_EXEC) build $(DOCKER_OPTIONS) . -f $(INFRA_DIRECTORY)Dockerfile -t $(IMAGE_TAG) -t $(IMAGE_TAG_LATEST)

push: env ## push application
	sudo $(DOCKER_EXEC) push $(DOCKER_OPTIONS) $(IMAGE_TAG)

DEPLOY_KUBER_CONTEXT?=
DEPLOYMENT_SUFFIX?=
export DEPLOYMENT_NAMESPACE?=$(APP_SPACE)
export DEPLOYMENT_NAME?=$(APP_NAME)$(DEPLOYMENT_SUFFIX)
export IMAGE?=$(IMAGE_TAG)
export IPV4POOL?=$(shell ${KUBECTL_EXEC} get configmap -n kube-system kube-workload-controller-config --context=${DEPLOY_KUBER_CONTEXT} -o jsonpath="{.data['config\.json']}" | grep ${DEPLOYMENT_NAME} | awk '{print substr($$2, 1, length($$2)-1)}')

deploy: ## deploy docker image
	$(ENVSUBST_EXEC) < $(INFRA_DIRECTORY)serviceaccount.yaml | $(KUBECTL_EXEC) apply $(KUBECTL_OPTIONS) --context=$(DEPLOY_KUBER_CONTEXT) -f -
	$(KUBECTL_EXEC) delete job $(DEPLOYMENT_NAME) -n $(DEPLOYMENT_NAMESPACE) --context=$(DEPLOY_KUBER_CONTEXT) || true
	IMAGE=$(IMAGE) $(ENVSUBST_EXEC) < $(INFRA_DIRECTORY)job.yaml | $(KUBECTL_EXEC) apply $(KUBECTL_OPTIONS) --context=$(DEPLOY_KUBER_CONTEXT) -f -

build_db:
	docker build -t kotlin-liquibase infra/db/.

stop_db:
	docker container rm -f kotlin-liquibase || true

run_db:
	make stop_db
	make build_db
	docker run -d --name kotlin-liquibase -p 5433:5432 kotlin-liquibase