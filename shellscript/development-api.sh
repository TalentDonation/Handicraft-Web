#!/bin/bash

function develop-api()
{
        cd ../handicraft-web/handicraft-api/
        sh mvnw -Dspring.profiles.active="development-api" clean spring-boot:run
}

develop-api

