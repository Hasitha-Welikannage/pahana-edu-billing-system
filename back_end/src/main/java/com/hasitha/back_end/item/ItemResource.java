package com.hasitha.back_end.item;

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

    ItemService itemService = new ItemService();

    @GET
    public Response getAll() {
        List<Item> items = itemService.findAll();
        ApiResponse apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, items);
        return Response.ok(apiResponse).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {

        Item item = itemService.findById(id);
        ApiResponse apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.READ_SUCCESS, item);
        return Response.ok(apiResponse).build();

    }

    @POST
    public Response create(Item item) {

        Item created = itemService.create(item);
        ApiResponse apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.CREATE_SUCCESS, created);
        return Response.status(Response.Status.CREATED).entity(apiResponse).build();

    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Item item) {

        Item updated = itemService.update(id, item);
        ApiResponse apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.UPDATE_SUCCESS, updated);
        return Response.ok(apiResponse).build();

    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        
        itemService.delete(id);
        ApiResponse apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.DELETE_SUCCESS, null);
        return Response.status(Response.Status.NO_CONTENT).entity(apiResponse).build();
        
    }
}
