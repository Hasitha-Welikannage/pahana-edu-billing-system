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

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final AuthService authService;

    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    public AuthResource() {
        this.authService = new AuthService();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request, @Context HttpServletRequest httpRequest) {
        User user = authService.login(request);
        httpRequest.getSession(true).setAttribute("user", user);
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LOGIN_SUCCESS, user))
                .build();
    }

    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LOGOUT_SUCCESS, null))
                .build();
    }

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
