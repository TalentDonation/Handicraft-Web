#!/bin/bash

function deployAdmin()
{
        cd ../handicraft-web/handicraft-admin/
        mvnw clean
        sh mvnw -Dspring.profiles.active="development-api" spring-boot:run
}

deployAdmin
