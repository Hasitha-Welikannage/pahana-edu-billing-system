package com.hasitha.back_end.authentication;

import com.hasitha.back_end.exceptions.MessageConstants;
import com.hasitha.back_end.response.ErrorResponse;
import com.hasitha.back_end.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
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
        String method = requestContext.getMethod();

        // Step 1: Skip auth checks for /auth (login, register, etc.)
        if (path.startsWith("auth")) {
            return;
        }

        // Step 2: Block all unauthenticated access
        if (user == null) {
            requestContext.abortWith(
                    Response
                            .status(Response.Status.UNAUTHORIZED)
                            .entity(new ErrorResponse(MessageConstants.UNAUTHORIZED_CODE, MessageConstants.UNAUTHORIZED_ACCESS, null))
                            .type(MediaType.APPLICATION_JSON)
                            .build()
            );
            return;
        }

        // Step 3: Restrict /users and /items to ADMIN only
        if ((path.startsWith("users")) && !"ADMIN".equalsIgnoreCase(user.getRole())) {
            requestContext.abortWith(
                    Response
                            .status(Response.Status.FORBIDDEN)
                            .entity(new ErrorResponse(MessageConstants.FORBIDDEN_CODE, MessageConstants.FORBIDDEN_ROLE, null))
                            .type(MediaType.APPLICATION_JSON)
                            .build()
            );
        }

        if (path.startsWith("items")) {
            // Check if the request method is one of the restricted methods
            if (("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method))
                    && !"ADMIN".equalsIgnoreCase(user.getRole())) {

                requestContext.abortWith(
                        Response
                                .status(Response.Status.FORBIDDEN)
                                .entity(new ErrorResponse(MessageConstants.FORBIDDEN_CODE, MessageConstants.FORBIDDEN_ROLE, null))
                                .type(MediaType.APPLICATION_JSON)
                                .build()
                );
            }
        }
    }
}
