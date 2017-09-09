#!/bin/bash

#  arguments for profile
function run()
{
	cd /app/handicraft-web/handicraft-core
	sh mvnw -Dspring.profiles.active="$1-core" install


}

run $1
