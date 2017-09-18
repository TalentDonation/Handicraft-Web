#!/bin/bash

# arguments for profile
function deploy()
{
	cd /app/handicraft-web/handicraft-api
	sh mvnw -Dspring.profiles.active="$1-api" spring-boot:run
}

deploy $1
