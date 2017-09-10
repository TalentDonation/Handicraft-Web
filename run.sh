#!/bin/bash
<<<<<<< HEAD

#  arguments for profile
function run()
{
=======
function run(){
>>>>>>> 89c767838628eaa78390dbe6030868a005f725c3
	cd /app/handicraft-web/handicraft-core
	sh mvnw -Dspring.profiles.active="$1-core" install


}
<<<<<<< HEAD

run $1
=======
run
>>>>>>> 89c767838628eaa78390dbe6030868a005f725c3
