.PHONY: api

ifndef DOCKER_COMPOSE_BIN
DOCKER_COMPOSE_BIN := docker compose
endif

# MAIN METHODS
COMPOSE := ${DOCKER_COMPOSE_BIN} -f support/docker-compose.yaml
setup: api-setup web

api-setup: db sleep db-client sleep api

# --rmi local removes the custom built dockerfiles
teardown:
	${COMPOSE} down -v --rmi local
	${COMPOSE} rm --force --stop -v

# BASE METHODS
db:
	${COMPOSE} up -d db

db-client: 
	${COMPOSE} up -d phpmyadmin

api:
	${COMPOSE} up -d --build api

# TODO - not needed for now
web: 
	${COMPOSE} up -d frontend

sleep: 
	sleep 5