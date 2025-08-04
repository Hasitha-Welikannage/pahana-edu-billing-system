package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.MessageConstants;
import com.hasitha.back_end.response.ApiResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST API resource class for managing users.
 * 
 * Handles CRUD operations such as retrieving, creating, updating, and deleting
 * users. Communicates with {@link UserService}.
 *
 * @author hasithawelikannage
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    // Default constructor (used in production)
    public UserResource() {
        this.userService = new UserService();
    }

    // Constructor for injection (used in tests)
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves a list of findAll users.
     *
     * @return HTTP 200 OK with a list of users in ApiResponse
     */
    @GET
    public Response findAll() {
        List<User> list = userService.findAll();
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, list))
                .build();
    }

    /**
     * Retrieves a single user by ID.
     *
     * @param id The ID of the user to retrieve
     * @return HTTP 200 OK with the user data in ApiResponse
     */
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") int id) {
        User user = userService.findById(id);
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.READ_SUCCESS, user))
                .build();
    }

    /**
     * Creates a new user.
     *
     * @param user The user data to create
     * @return HTTP 201 Created with the created user in ApiResponse
     */
    @POST
    public Response create(User user) {
        User createdUser = userService.create(user);
        return Response
                .status(Response.Status.CREATED)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.CREATE_SUCCESS, createdUser))
                .build();
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
    public Response update(@PathParam("id") int id, User user) {
        User updatedUser = userService.update(id, user);
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.UPDATE_SUCCESS, updatedUser))
                .build();
    }

    /**
     * Deletes a user by ID.
     *
     * @param id The ID of the user to delete
     * @return HTTP 200 OK with a success message in ApiResponse
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        userService.delete(id);
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.DELETE_SUCCESS, null))
                .build();
    }
}
