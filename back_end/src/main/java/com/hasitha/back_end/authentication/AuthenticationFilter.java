package com.hasitha.back_end.authentication;

import com.hasitha.back_end.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@PreMatching
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        User user = (User) request.getSession().getAttribute("user");

        // Step 1: Skip auth checks for /auth (login, register, etc.)
        if (path.startsWith("auth/login")) {
            return;
        }

        // Step 2: Block all unauthenticated access
        if (user == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Login required").build());
            return;
        }

        // Step 3: Restrict /users and /items to ADMIN only
        if ((path.startsWith("users") || path.startsWith("items")) && !"ADMIN".equalsIgnoreCase(user.getRole())) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("Access denied. Admins only.").build());
        }
    }
}
