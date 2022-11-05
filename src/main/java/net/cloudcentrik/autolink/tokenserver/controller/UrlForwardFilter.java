package net.cloudcentrik.autolink.tokenserver.controller;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * redirect all other url except /api to /
 */
@Component
public class UrlForwardFilter implements Filter {

    private final String REST_API_PATTERN = "^\\/api\\/(.+)$";
    private final String POINT_EXCLUSION_PATTERN = "^([^.]+)$";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String requestURI = servletRequest.getRequestURI();
        String contextPath = servletRequest.getContextPath();

        if(!requestURI.equals(contextPath) && !requestURI.matches(REST_API_PATTERN) && requestURI.matches(POINT_EXCLUSION_PATTERN)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/");
            dispatcher.forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

}