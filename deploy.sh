#!/bin/bash

function deploy()
{
	cd /app/handicraft-web/handicraft-api
	sh mvnw spring-boot:run
}

deploy
