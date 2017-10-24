#!/bin/bash

# arguments for profile
# 1 : profiles - (development or production) , 2 : projects - (api or admin)
function deploy()
{
	echo ---------- build $2 core ----------
 	cd /app/handicraft-web/handicraft-core
 	sh mvnw clean
 	sh mvnw -Dspring.profiles.active="$1-core" install

	if [ $2 == "api" ]	;	then
		echo ---------- build $2 $1 ----------
		cd /app/handicraft-web/handicraft-$2/
		sh mvnw -Dspring.profiles.active="$1-$2" spring-boot:run
	fi

}

deploy $1	$2
