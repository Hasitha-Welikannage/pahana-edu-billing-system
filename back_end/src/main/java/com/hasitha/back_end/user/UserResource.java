/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.AppException;
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

/**
 *
 * @author hasithawelikannage
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    UserDAOInterface userDao = new UserDAO();

    // ----------- GET ALL USERS -----------
    @GET
    public Response all() {
        try {
            List<User> list = userDao.findAll();
            if (list == null || list.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Users not found")
                        .build();
            }
            return Response.ok(list).build();
        } catch (AppException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching users: " + ex.getMessage())
                    .build();
        }
    }

    // ----------- GET USER BY ID -----------
    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") int id) {
        try {
            User user = userDao.findById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("User not found")
                        .build();
            }
            return Response.ok(user).build();
        } catch (AppException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching user: " + e.getMessage())
                    .build();
        }
    }

    // ----------- CREATE USER -----------
    @POST
    public Response createUser(User user) {
        try {
            User createdUser = userDao.create(user);
            return Response.status(Response.Status.CREATED)
                    .entity(createdUser)
                    .build();
        } catch (AppException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error creating user: " + e.getMessage()+"\n" + e.getCause())
                    .build();
        }
    }

    // ----------- UPDATE USER BY ID -----------
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, User user) {
        try {
            User updatedUser = userDao.update(id, user);
            if (updatedUser == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("User not found")
                        .build();
            }
            return Response.ok(updatedUser).build();
        } catch (AppException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating user: " + e.getMessage())
                    .build();
        }
    }

    // ----------- DELETE USER BY ID -----------
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        try {
            User user = userDao.findById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("User not found")
                        .build();
            }

            userDao.delete(id);
            return Response.noContent().build();
        } catch (AppException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting user: " + e.getMessage())
                    .build();
        }
    }
}
