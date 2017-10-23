#!/bin/bash

# arguments for profile
function deploy()
{
	cd /app/handicraft-web/handicraft-$2

	if [$2 -eq "api"];
	then
		sh mvnw -Dspring.profiles.active="$1-api" spring-boot:run
	else
		sh mvnw spring-boot:run
	fi
}

deploy $1	$2
