package com.hasitha.back_end.item;

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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hasithawelikannage
 */
@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {

    private final ItemDAOInterface dao = new ItemDAO();

    @GET
    public Response getAll() {
        try {
            List<Item> items = dao.findAll();
            return Response.ok(items).build();
        } catch (AppException e) {
            return Response.serverError().entity("Error fetching items").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        try {
            Item item = dao.findById(id);
            if (item == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Item not found").build();
            }
            return Response.ok(item).build();
        } catch (AppException e) {
            return Response.serverError().entity("Error fetching item").build();
        }
    }

    @POST
    public Response create(Item item) {
        try {
            Item created = dao.create(item);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (AppException e) {
            return Response.serverError().entity("Error creating item").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Item item) {
        try {
            Item updated = dao.update(id, item);
            if (updated == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Item not found").build();
            }
            return Response.ok(updated).build();
        } catch (AppException e) {
            return Response.serverError().entity("Error updating item" + e.getCause()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            dao.delete(id);
            return Response.noContent().build();
        } catch (AppException e) {
            return Response.serverError().entity("Error deleting item").build();
        }
    }
}
