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

/**
 * RESTful resource class for managing items. Provides endpoints for CRUD
 * operations on items.
 *
 * Endpoint base path: /items Consumes and produces JSON data.
 *
 */
@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {

    private final ItemService itemService;

    // Default constructor (used in production)
    public ItemResource() {
        this.itemService = new ItemService();
    }

    // Constructor for injection (used in tests)
    public ItemResource(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Retrieves a list of all items.
     *
     * @return Response containing the list of items and a success message.
     */
    @GET
    public Response findAll() {
        List<Item> items = itemService.findAll();
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, items))
                .build();
    }

    /**
     * Retrieves a specific item by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return Response containing the requested item and a success message.
     */
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") int id) {
        Item item = itemService.findById(id);
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.READ_SUCCESS, item))
                .build();
    }

    /**
     * Creates a new item.
     *
     * @param item The item object to be created.
     * @return Response with the created item and a creation success message.
     */
    @POST
    public Response create(Item item) {
        Item createdItem = itemService.create(item);
        return Response
                .status(Response.Status.CREATED)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.CREATE_SUCCESS, createdItem))
                .build();
    }

    /**
     * Updates an existing item by ID.
     *
     * @param id The ID of the item to update.
     * @param item The updated item data.
     * @return Response containing the updated item and a success message.
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Item item) {
        Item updatedItem = itemService.update(id, item);
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.UPDATE_SUCCESS, updatedItem))
                .build();
    }

    /**
     * Deletes an item by ID.
     *
     * @param id The ID of the item to delete.
     * @return Response with success message and HTTP 200 OK status.
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        itemService.delete(id);
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.DELETE_SUCCESS, null))
                .build();
    }
}
