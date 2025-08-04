package com.hasitha.back_end.customer;

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
 * RESTful resource class that handles HTTP requests related to Customer
 * operations. Exposes endpoints to perform CRUD operations using the
 * CustomerService.
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private final CustomerService customerService;

    /**
     * Constructs the resource with a custom CustomerService (for testing or
     * DI).
     *
     * @param customerService the CustomerService instance
     */
    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Default constructor initializing with a new instance of CustomerService.
     */
    public CustomerResource() {
        this.customerService = new CustomerService();
    }

    /**
     * Retrieves a list of all customers.
     *
     * @return HTTP 200 OK with a list of customers wrapped in ApiResponse
     */
    @GET
    public Response findAll() {
        List<Customer> list = customerService.findAll();
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, list))
                .build();
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id the unique identifier of the customer
     * @return HTTP 200 OK with the customer details wrapped in ApiResponse
     */
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") int id) {
        Customer customer = customerService.findById(id);
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.READ_SUCCESS, customer))
                .build();
    }

    /**
     * Creates a new customer.
     *
     * @param customer the customer data from request body
     * @return HTTP 201 Created with the created customer wrapped in ApiResponse
     */
    @POST
    public Response create(Customer customer) {
        Customer createdCustomer = customerService.create(customer);
        return Response
                .status(Response.Status.CREATED)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.CREATE_SUCCESS, createdCustomer))
                .build();
    }

    /**
     * Updates an existing customer by ID.
     *
     * @param id the ID of the customer to update
     * @param customer the updated customer data
     * @return HTTP 200 OK with the updated customer wrapped in ApiResponse
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Customer customer) {
        Customer updatedCustomer = customerService.update(id, customer);
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.UPDATE_SUCCESS, updatedCustomer))
                .build();
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id the ID of the customer to delete
     * @return HTTP 200 OK with a success message wrapped in ApiResponse
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        customerService.delete(id);
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.DELETE_SUCCESS, null))
                .build();
    }
}
