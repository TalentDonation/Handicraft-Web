package com.handicraft.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
@Slf4j
public class ErrorConfig extends ServerProperties{

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        super.customize(container);
        container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/view/error401.jsp"));
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/view/error404.jsp"));
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/view/error500.jsp"));
        container.addErrorPages(new ErrorPage(HttpStatus.SERVICE_UNAVAILABLE, "/view/error503.jsp"));
    }
}
