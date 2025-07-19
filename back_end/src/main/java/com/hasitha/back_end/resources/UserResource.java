/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.resources;

import com.hasitha.back_end.dao.UserDAO;
import com.hasitha.back_end.dao.UserDAOInterface;
import com.hasitha.back_end.exceptions.DaoException;
import com.hasitha.back_end.model.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
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

    @GET
    public Response all() throws SQLException {
        try {
            List<User> list = userDao.findAll();

            if (list == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Users not found")
                        .build();
            }
            return Response.ok(list).build();
        } catch (DaoException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        try {
            User user = userDao.findById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("User not found")
                        .build();
            }
            return Response.ok(user).build();
        } catch (DaoException e) {
            // Handle DAO error gracefully, return 500 with message
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching user: " + e.getMessage())
                    .build();
        }
    }
}
