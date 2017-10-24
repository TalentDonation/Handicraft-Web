
#!/bin/bash

# arguments for profile
# 1 : profiles - (development or production) , 2 : projects - (api or admin)
function deploy()
{
	echo ---------- deploy $2 $1 ----------
	cd /app/handicraft-web/handicraft-$2/
	sh mvnw -Dspring.profiles.active="$1-$2" spring-boot:run
}

deploy $1       $2
