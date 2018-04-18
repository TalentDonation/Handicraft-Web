package com.handicraft.api.config.filter;


import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseFileter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);
        responseWrapper.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        filterChain.doFilter(servletRequest, responseWrapper);
        responseWrapper.copyBodyToResponse();
    }

    @Override
    public void destroy() {
    }
}
