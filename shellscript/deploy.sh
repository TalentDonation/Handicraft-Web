#!/bin/bash

# arguments for profile
function deploy()
{
	if test "$2" = "api" ;then
		cd /app/handicraft-web/handicraft-api
		sh mvnw -Dspring.profiles.active="$1-api" spring-boot:run
	else
		cd /app/handicraft-web/handicraft-admin
		sh mvnw spring-boot:run
	fi
}

deploy $1	$2
