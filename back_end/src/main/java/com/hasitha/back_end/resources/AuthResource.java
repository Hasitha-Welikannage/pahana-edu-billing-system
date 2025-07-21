/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.resources;

import com.hasitha.back_end.dao.UserDAO;
import com.hasitha.back_end.dao.UserDAOInterface;
import com.hasitha.back_end.dto.LoginRequest;
import com.hasitha.back_end.exceptions.DaoException;
import com.hasitha.back_end.model.User;
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

    private final UserDAOInterface userDao = new UserDAO();

    @POST
    @Path("/login")
    public Response login(LoginRequest request, @Context HttpServletRequest httpRequest) {
        try {
            User user = userDao.findByUsernameAndPassword(request.getUsername(), request.getPassword());
            if (user != null) {
                httpRequest.getSession(true).setAttribute("user", user);
                return Response.ok(user).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Invalid username or password")
                        .build();
            }
        } catch (DaoException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Login error: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest httpRequest) {
        httpRequest.getSession().invalidate();
        return Response.ok("Logged out successfully").build();
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
