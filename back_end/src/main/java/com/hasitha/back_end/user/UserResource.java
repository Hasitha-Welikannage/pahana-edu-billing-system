/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.exceptions.MessageConstants;
import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import com.hasitha.back_end.response.ApiResponse;
import com.hasitha.back_end.response.ErrorResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    UserService userService = new UserService();
    ApiResponse apiResponse;
    ErrorResponse errorResponse;

    // ----------- GET ALL USERS -----------
    @GET
    public Response all() {
        try {

            List<User> list = userService.findAll();

            apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, list);

            return Response
                    .status(Response.Status.OK)
                    .entity(apiResponse)
                    .build();

        } catch (DatabaseException e) {

            errorResponse = new ErrorResponse(MessageConstants.SERVER_ERROR_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();

        } catch (ValidationException e) {

            errorResponse = new ErrorResponse(MessageConstants.VALIDATION_ERROR_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();

        } catch (NotFoundException e) {

            errorResponse = new ErrorResponse(MessageConstants.NOT_FOUND_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(errorResponse)
                    .build();
        }
    }

    // ----------- GET USER BY ID -----------
    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") int id) {
        try {
            User user = userService.findById(id);

            apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.READ_SUCCESS, user);

            return Response
                    .status(Response.Status.OK)
                    .entity(apiResponse)
                    .build();

        } catch (DatabaseException e) {

            errorResponse = new ErrorResponse(MessageConstants.SERVER_ERROR_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();

        } catch (ValidationException e) {

            errorResponse = new ErrorResponse(MessageConstants.VALIDATION_ERROR_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();

        } catch (NotFoundException e) {

            errorResponse = new ErrorResponse(MessageConstants.NOT_FOUND_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(errorResponse)
                    .build();
        }
    }

    // ----------- CREATE USER -----------
    @POST
    public Response createUser(User user) {
        try {

            User createdUser = userService.create(user);

            apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.CREATE_SUCCESS, createdUser);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(apiResponse)
                    .build();

        } catch (DatabaseException e) {

            errorResponse = new ErrorResponse(MessageConstants.SERVER_ERROR_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();

        } catch (ValidationException e) {

            errorResponse = new ErrorResponse(MessageConstants.VALIDATION_ERROR_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();

        }
    }

    // ----------- UPDATE USER BY ID -----------
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, User user) {
        try {
            User updatedUser = userService.update(id, user);

            apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.UPDATE_SUCCESS, updatedUser);

            return Response
                    .status(Response.Status.OK)
                    .entity(apiResponse)
                    .build();

        } catch (DatabaseException e) {

            errorResponse = new ErrorResponse(MessageConstants.SERVER_ERROR_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();

        } catch (ValidationException e) {

            errorResponse = new ErrorResponse(MessageConstants.VALIDATION_ERROR_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();

        } catch (NotFoundException e) {

            errorResponse = new ErrorResponse(MessageConstants.NOT_FOUND_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(errorResponse)
                    .build();

        }
    }

    // ----------- DELETE USER BY ID -----------
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        try {

            userService.delete(id);

            apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.DELETE_SUCCESS, null);

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(apiResponse)
                    .build();

        } catch (DatabaseException e) {

            errorResponse = new ErrorResponse(MessageConstants.SERVER_ERROR_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();

        } catch (ValidationException e) {

            errorResponse = new ErrorResponse(MessageConstants.VALIDATION_ERROR_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();

        } catch (NotFoundException e) {

            errorResponse = new ErrorResponse(MessageConstants.NOT_FOUND_CODE, e.getMessage(), null);

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(errorResponse)
                    .build();
        }
    }
}
