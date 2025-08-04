package com.hasitha.back_end.authentication;

import com.hasitha.back_end.exceptions.MessageConstants;
import com.hasitha.back_end.response.ApiResponse;
import com.hasitha.back_end.response.ErrorResponse;
import com.hasitha.back_end.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST resource class that handles authentication operations such as login,
 * logout, and fetching the currently logged-in user.
 */
@Path("/auth") // Base path for all endpoints in this resource
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final AuthService authService;

    /**
     * Constructor used for dependency injection.
     *
     * @param authService the authentication service
     */
    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Default constructor used by the JAX-RS runtime or manually. Initializes
     * with a default AuthService instance.
     */
    public AuthResource() {
        this.authService = new AuthService();
    }

    /**
     * Logs in a user by validating the credentials and saving the user in the
     * session.
     *
     * @param request login credentials (username and password)
     * @param httpRequest injected HTTP request to access the session
     * @return HTTP 200 with user data if successful, or validation error
     */
    @POST
    @Path("/login")
    public Response login(LoginRequest request, @Context HttpServletRequest httpRequest) {
        User user = authService.login(request); // Throws exception if invalid
        httpRequest.getSession(true).setAttribute("user", user); // Store user in session

        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LOGIN_SUCCESS, user))
                .build();
    }

    /**
     * Logs out the currently logged-in user by invalidating the session.
     *
     * @param httpRequest injected HTTP request to access the session
     * @return HTTP 200 if logout was successful
     */
    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false); // Get existing session if any
        if (session != null) {
            session.invalidate(); // Clear session
        }
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LOGOUT_SUCCESS, null))
                .build();
    }

    /**
     * Retrieves the currently logged-in user's information from the session.
     *
     * @param httpRequest injected HTTP request to access the session
     * @return HTTP 200 with user info if logged in, or 401 unauthorized
     * otherwise
     */
    @GET
    @Path("/me")
    public Response currentUser(@Context HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse(MessageConstants.UNAUTHORIZED_CODE, "You are not logged in.", null))
                    .build();
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse(MessageConstants.UNAUTHORIZED_CODE, "You are not logged in.", null))
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, "User is currently logged in.", user))
                .build();
    }
}
