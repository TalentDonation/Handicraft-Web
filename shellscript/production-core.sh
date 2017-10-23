#!/bin/bash

function production-core()
{
        cd ../handicraft-web/handicraft-core/
	       sh mvnw clean
        sh mvnw -Dspring.profiles.active="production-core" install
}

production-core
