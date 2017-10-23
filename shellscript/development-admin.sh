#!/bin/bash

function deployAdmin()
{
        cd ../handicraft-web/handicraft-admin/
        mvnw clean
        sh mvnw -Dspring.profiles.active="development-admin" spring-boot:run
}

deployAdmin
