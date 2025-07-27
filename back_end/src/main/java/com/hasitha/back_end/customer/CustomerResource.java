/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.customer;

import com.hasitha.back_end.exceptions.AppException;
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
 *
 * @author hasithawelikannage
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    CustomerService customerService = new CustomerService();
    ApiResponse apiResponse;

    @GET
    public Response getAll() {

        List<Customer> list = customerService.findAll();

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, list);

        return Response
                .status(Response.Status.OK)
                .entity(apiResponse)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {

        Customer customer = customerService.findById(id);

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.READ_SUCCESS, customer);

        return Response
                .status(Response.Status.OK)
                .entity(apiResponse)
                .build();

    }

    @POST
    public Response create(Customer customer) {

        Customer createdCustomer = customerService.create(customer);

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.CREATE_SUCCESS, createdCustomer);

        return Response
                .status(Response.Status.CREATED)
                .entity(apiResponse)
                .build();

    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Customer customer) {
        
        Customer updatedCustomer = customerService.update(id, customer);

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.UPDATE_SUCCESS, updatedCustomer);

        return Response
                .status(Response.Status.OK)
                .entity(apiResponse)
                .build();

    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {

        customerService.delete(id);

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.DELETE_SUCCESS, null);

        return Response
                .status(Response.Status.NO_CONTENT)
                .entity(apiResponse)
                .build();

    }
}
