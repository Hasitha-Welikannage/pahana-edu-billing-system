/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.customer;

import com.hasitha.back_end.exceptions.DaoException;
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

    private final CustomerDAOInterface dao = new CustomerDAO();

    @GET
    public Response getAll() {
        try {
            List<Customer> customers = dao.findAll();
            return Response.ok(customers).build();
        } catch (DaoException e) {
            return Response.serverError().entity("Failed to fetch customers").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        try {
            Customer customer = dao.findById(id);
            if (customer == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            }
            return Response.ok(customer).build();
        } catch (DaoException e) {
            return Response.serverError().entity("Failed to fetch customer").build();
        }
    }

    @POST
    public Response create(Customer customer) {
        try {
            Customer created = dao.create(customer);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (DaoException e) {
            return Response.serverError().entity("Failed to create customer").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Customer customer) {
        try {
            Customer updated = dao.update(id, customer);
            if (updated == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
            }
            return Response.ok(updated).build();
        } catch (DaoException e) {
            return Response.serverError().entity("Failed to update customer").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            dao.delete(id);
            return Response.noContent().build();
        } catch (DaoException e) {
            return Response.serverError().entity("Failed to delete customer").build();
        }
    }
}
