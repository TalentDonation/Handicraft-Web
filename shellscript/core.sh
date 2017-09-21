#!/bin/bash

#  arguments for profile
function coreInstall()
{
	cd /app/handicraft-web/handicraft-core
	sh mvnw -Dspring.profiles.active="$1-core" install


}

coreInstall $1
