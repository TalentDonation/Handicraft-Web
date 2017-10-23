#!/bin/bash

function deployAdmin()
{
        cd ../handicraft-web/handicraft-admin/
        sh mvnw clean
        sh mvnw -Dspring.profiles.active="production-admin" spring-boot:run
}

deployAdmin
