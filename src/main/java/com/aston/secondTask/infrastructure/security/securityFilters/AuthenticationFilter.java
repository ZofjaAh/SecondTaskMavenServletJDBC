package com.aston.secondTask.infrastructure.security.securityFilters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter  implements Filter {


        @Override
        public void doFilter(final ServletRequest request,
                             final ServletResponse response,
                             final FilterChain filterChain) throws IOException, ServletException {

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
            if (path.startsWith("/login") || path.startsWith("/registration")
                    || path.startsWith("/images") || path.startsWith("/rest")) {
                filterChain.doFilter(request, response);
                return;
            }
            HttpSession session = httpRequest.getSession(false);

            if (session == null) {
                httpRequest.getRequestDispatcher("/WEB-INF/view/login.html").forward(httpRequest, httpResponse);
            } else {
                filterChain.doFilter(request, response);
            }
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void destroy() {

        }


    }

