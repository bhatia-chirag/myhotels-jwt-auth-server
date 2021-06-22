package com.myhotels.authorizationserver.filters;

import javax.servlet.*;
import java.io.IOException;

public class CustomFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}
