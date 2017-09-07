#!/bin/bash

function run()
{
	cd /app/handicraft-web/handicraft-core
	sh mvnw install
	#cd ../handicraft-api
	#sh mvnw spring-boot:run
}

run
