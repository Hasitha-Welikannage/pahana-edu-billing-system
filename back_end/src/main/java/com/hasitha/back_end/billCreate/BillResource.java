package com.hasitha.back_end.billCreate;

import com.hasitha.back_end.bill.BillDTO;
import com.hasitha.back_end.billItem.BillItemDTO;
import com.hasitha.back_end.exceptions.MessageConstants;
import com.hasitha.back_end.response.ApiResponse;
import com.hasitha.back_end.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

/**
 * REST resource for handling billing operations. This class defines endpoints
 * for creating a bill, retrieving all bills, and fetching bill items by bill
 * ID.
 */
@Path("/bills")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillResource {

    private final BillCreateService billCreateService = new BillCreateService();

    /**
     * Endpoint to create a new bill.
     *
     * <p>
     * <b>POST /bills</b></p>
     * Requires the user to be logged in (retrieved from session). Accepts a
     * {@link CreateBillRequest} JSON object with customer ID and item list.
     * Returns the created bill with status 201 (Created).
     *
     * @param req the request body containing bill details
     * @param httpRequest the HTTP servlet request to extract logged-in user
     * from session
     * @return Response with created {@link BillDTO} wrapped in
     * {@link ApiResponse}, or 401 if not logged in
     */
    @POST
    public Response createBill(CreateBillRequest req, @Context HttpServletRequest httpRequest) {

        User user = (User) httpRequest.getSession().getAttribute("user");

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Login required").build();
        }

        req.setUserId(user.getId());
        BillDTO bill = billCreateService.createBill(req);
        return Response
                .status(Response.Status.CREATED)
                .entity(new ApiResponse(MessageConstants.CREATE_SUCCESS, MessageConstants.CREATE_SUCCESS, bill))
                .build();
    }

    /**
     * Endpoint to retrieve a list of all bills (bill headers).
     *
     * <p>
     * <b>GET /bills</b></p>
     * Returns all available bills with status 200 (OK).
     *
     * @return Response containing a list of {@link BillDTO} wrapped in
     * {@link ApiResponse}
     */
    @GET
    public Response getAllBills() {
        List<BillDTO> bills = billCreateService.getAllBills();
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, bills))
                .build();
    }

    /**
     * Endpoint to retrieve all bill items for a specific bill.
     *
     * <p>
     * <b>GET /bills/{id}/items</b></p>
     * Accepts a bill ID as a path parameter and returns all corresponding bill
     * items.
     *
     * @param billId the ID of the bill whose items are to be retrieved
     * @return Response with a list of {@link BillItemDTO} wrapped in
     * {@link ApiResponse}
     */
    @GET
    @Path("/{id}/items")
    public Response getBillItemsByBillId(@PathParam("id") int billId) {
        List<BillItemDTO> billItems = billCreateService.getBillItemsByBillId(billId);
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, billItems))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getBill(@PathParam("id") int id) {
        BillDTO bill = billCreateService.getBill(id);
        return Response
                .status(Response.Status.OK)
                .entity(new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.READ_SUCCESS, bill))
                .build();
    }
}
