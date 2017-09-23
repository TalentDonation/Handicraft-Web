#!/bin/bash

function deployAdmin()
{
        cd ../handicraft-web/handicraft-admin/
        sh mvnw clean spring-boot:run
}

deployAdmin
