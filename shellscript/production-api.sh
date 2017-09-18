#!/bin/bash

function production-api()
{
        cd ../handicraft-web/handicraft-api/
        sh mvnw -Dspring.profiles.active="production-api" clean spring-boot:run
}

production-api

