/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.authentication;

import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.exceptions.MessageConstants;
import com.hasitha.back_end.exceptions.ValidationException;
import com.hasitha.back_end.response.ApiResponse;
import com.hasitha.back_end.response.ErrorResponse;
import com.hasitha.back_end.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author hasithawelikannage
 */
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final AuthService authService = new AuthService();
    ApiResponse apiResponse;
    ErrorResponse errorResponse;

    @POST
    @Path("/login")
    public Response login(LoginRequest request, @Context HttpServletRequest httpRequest) {
        try {
            User user = authService.login(request);
            if (user != null) {
                httpRequest.getSession(true).setAttribute("user", user);

                apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LOGIN_SUCCESS, user);

                return Response.ok(apiResponse).build();
            } else {

                errorResponse = new ErrorResponse(MessageConstants.UNAUTHORIZED_CODE, MessageConstants.INVALID_CREDENTIALS, null);

                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(errorResponse)
                        .build();
            }
        } catch (ValidationException e) {

            errorResponse = new ErrorResponse(MessageConstants.VALIDATION_ERROR_CODE, e.getMessage(), null);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();
        } catch (DatabaseException e) {

            errorResponse = new ErrorResponse(MessageConstants.SERVER_ERROR_CODE, e.getMessage(), null);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest httpRequest) {
        httpRequest.getSession().invalidate();

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LOGOUT_SUCCESS, null);

        return Response.ok(apiResponse).build();

    }

    @GET
    @Path("/me")
    public Response currentUser(@Context HttpServletRequest httpRequest) {
        User user = (User) httpRequest.getSession().getAttribute("user");
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Not logged in")
                    .build();
        }
        return Response.ok(user).build();
    }
}
