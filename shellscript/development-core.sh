#!/bin/bash

function develop-core()
{
	cd ../handicraft-web/handicraft-core/
	sh mvnw clean
	sh mvnw -Dspring.profiles.active="development-core" install
}

develop-core
