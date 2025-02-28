.PHONY: api

ifndef DOCKER_COMPOSE_BIN
DOCKER_COMPOSE_BIN := docker compose
endif

# MAIN METHODS
COMPOSE := ${DOCKER_COMPOSE_BIN} -f support/docker-compose.yaml
setup: api-setup web

api-setup: mysql sleep api

teardown:
	${COMPOSE} down -v
	${COMPOSE} rm --force --stop -v

# BASE METHODS
api:
	${COMPOSE} up -d --build backend

mysql:
	${COMPOSE} up -d mysql

web: 
	${COMPOSE} up -d frontend

sleep: 
	sleep 5