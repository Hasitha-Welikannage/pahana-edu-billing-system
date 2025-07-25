/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
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

    // ----------- GET ALL USERS -----------
    @GET
    public Response all() {
        try {

            List<User> list = userService.findAll();

            return Response
                    .status(Response.Status.OK)
                    .entity(list)
                    .build();

        } catch (DatabaseException e) {

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();

        } catch (ValidationException e) {

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (NotFoundException e) {

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    // ----------- GET USER BY ID -----------
    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") int id) {
        try {
            User user = userService.findById(id);

            return Response
                    .status(Response.Status.OK)
                    .entity(user)
                    .build();

        } catch (DatabaseException e) {

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();

        } catch (ValidationException e) {

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (NotFoundException e) {

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    // ----------- CREATE USER -----------
    @POST
    public Response createUser(User user) {
        try {

            User createdUser = userService.create(user);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(createdUser)
                    .build();

        } catch (DatabaseException e) {

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();

        } catch (ValidationException e) {

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        }
    }

    // ----------- UPDATE USER BY ID -----------
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, User user) {
        try {
            User updatedUser = userService.update(id, user);

            return Response
                    .status(Response.Status.OK)
                    .entity(updatedUser)
                    .build();

        } catch (DatabaseException e) {

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();

        } catch (ValidationException e) {

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (NotFoundException e) {

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();

        }
    }

    // ----------- DELETE USER BY ID -----------
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        try {

            userService.delete(id);

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();

        } catch (DatabaseException e) {

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();

        } catch (ValidationException e) {

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (NotFoundException e) {

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
