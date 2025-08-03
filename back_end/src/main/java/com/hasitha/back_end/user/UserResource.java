package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.MessageConstants;
import com.hasitha.back_end.response.ApiResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST API resource class for managing users.
 * <p>
 * Handles CRUD operations such as retrieving, creating, updating, and deleting
 * users. Communicates with {@link UserService}.
 * </p>
 *
 * @author hasithawelikannage
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService = new UserService();
    private ApiResponse apiResponse;

    /**
     * Retrieves a list of all users.
     *
     * @return HTTP 200 OK with a list of users in ApiResponse
     */
    @GET
    public Response all() {
        List<User> list = userService.findAll();
        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, list);
        return Response.status(Response.Status.OK).entity(apiResponse).build();
    }

    /**
     * Retrieves a single user by ID.
     *
     * @param id The ID of the user to retrieve
     * @return HTTP 200 OK with the user data in ApiResponse
     */
    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") int id) {
        User user = userService.findById(id);
        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.READ_SUCCESS, user);
        return Response.status(Response.Status.OK).entity(apiResponse).build();
    }

    /**
     * Creates a new user.
     *
     * @param user The user data to create
     * @return HTTP 201 Created with the created user in ApiResponse
     */
    @POST
    public Response createUser(User user) {
        User createdUser = userService.create(user);
        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.CREATE_SUCCESS, createdUser);
        return Response.status(Response.Status.CREATED).entity(apiResponse).build();
    }

    /**
     * Updates an existing user by ID.
     *
     * @param id The ID of the user to update
     * @param user The updated user data
     * @return HTTP 200 OK with the updated user in ApiResponse
     */
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, User user) {
        User updatedUser = userService.update(id, user);
        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.UPDATE_SUCCESS, updatedUser);
        return Response.status(Response.Status.OK).entity(apiResponse).build();
    }

    /**
     * Deletes a user by ID.
     *
     * @param id The ID of the user to delete
     * @return HTTP 200 OK with a success message in ApiResponse
     */
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        userService.delete(id);
        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.DELETE_SUCCESS, null);
        return Response.status(Response.Status.OK).entity(apiResponse).build();
    }
}
