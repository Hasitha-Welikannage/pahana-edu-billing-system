/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.MessageConstants;
import com.hasitha.back_end.response.ApiResponse;
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
    
    // ----------- GET ALL USERS -----------
    @GET
    public Response all() {

        List<User> list = userService.findAll();

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, list);

        return Response
                .status(Response.Status.OK)
                .entity(apiResponse)
                .build();

    }

    // ----------- GET USER BY ID -----------
    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") int id) {

        User user = userService.findById(id);

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.READ_SUCCESS, user);

        return Response
                .status(Response.Status.OK)
                .entity(apiResponse)
                .build();

    }

    // ----------- CREATE USER -----------
    @POST
    public Response createUser(User user) {

        User createdUser = userService.create(user);

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.CREATE_SUCCESS, createdUser);

        return Response
                .status(Response.Status.CREATED)
                .entity(apiResponse)
                .build();

    }

    // ----------- UPDATE USER BY ID -----------
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, User user) {

        User updatedUser = userService.update(id, user);

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.UPDATE_SUCCESS, updatedUser);

        return Response
                .status(Response.Status.OK)
                .entity(apiResponse)
                .build();

    }

    // ----------- DELETE USER BY ID -----------
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {

        userService.delete(id);

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.DELETE_SUCCESS, null);

        return Response
                .status(Response.Status.NO_CONTENT)
                .entity(apiResponse)
                .build();

    }
}
