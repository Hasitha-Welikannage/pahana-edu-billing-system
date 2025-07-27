/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.billCreate;

import com.hasitha.back_end.bill.Bill;
import com.hasitha.back_end.billItem.BillItem;
import com.hasitha.back_end.exceptions.AppException;
import com.hasitha.back_end.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
@Path("/bills")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillResource {

    private final BillCreateService svc = new BillCreateService();

    @Context
    private HttpServletRequest request;

    /**
     * POST /bills : create new bill
     * @param req
     * @return 
     */
    @POST
    public Response createBill(CreateBillRequest req) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Login required").build();
            }

            Bill bill = svc.createBill(user.getId(), req);
            return Response.status(Response.Status.CREATED).entity(bill).build();

        } catch (AppException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * GET /bills : list headers only
     * @return 
     */
    @GET
    public Response all() {
        try {
            return Response.ok(svc.getAllBills()).build();
        } catch (AppException e) {
            return Response.serverError().entity("Could not fetch bills").build();
        }
    }

    /**
     * GET /bills/{id}/items : line items for bill
     * @param billId
     * @return 
     */
    @GET
    @Path("/{id}/items")
    public Response items(@PathParam("id") int billId) {
        try {
            List<BillItem> list = svc.getItemsForBill(billId);
            return Response.ok(list).build();
        } catch (AppException e) {
            return Response.serverError().entity("Could not fetch items").build();
        }
    }
}
