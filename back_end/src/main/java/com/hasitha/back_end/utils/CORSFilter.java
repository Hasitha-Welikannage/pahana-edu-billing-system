package com.hasitha.back_end.utils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter that enables Cross-Origin Resource Sharing (CORS) for all incoming
 * requests.
 *
 * This allows a frontend (for example, running on http://localhost:5173) to
 * communicate with this backend server by handling CORS headers.
 *
 * Applies to all request paths.
 */
@WebFilter("/*") // Apply this filter to all routes
public class CORSFilter implements Filter {

    /**
     * Initialization method for the filter.
     *
     * Currently no initialization logic is needed.
     */
    @Override
    public void init(FilterConfig filterConfig) {
        // No setup required
    }

    /**
     * This method is called for every HTTP request. It sets CORS headers to
     * allow the frontend to access backend APIs, and handles preflight OPTIONS
     * requests.
     *
     * @param req the incoming ServletRequest
     * @param res the outgoing ServletResponse
     * @param chain the filter chain to continue request processing
     * @throws IOException if an input/output error occurs
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        // Cast to HTTP-specific request/response
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        // Allow requests from frontend (adjust the origin as needed)
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");

        // Specify which headers can be sent in the request
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // Specify which HTTP methods are allowed
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        // Allow cookies and credentials (like sessions)
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // If the request is a preflight OPTIONS request, respond with OK
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Continue processing the request
        chain.doFilter(req, res);
    }

    /**
     * Cleanup method for the filter.
     *
     * Currently no cleanup logic is needed.
     */
    @Override
    public void destroy() {
        // No cleanup required
    }
}
