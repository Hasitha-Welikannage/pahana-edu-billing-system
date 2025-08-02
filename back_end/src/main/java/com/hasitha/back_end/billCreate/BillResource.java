package com.hasitha.back_end.billCreate;

import com.hasitha.back_end.bill.BillDTO;
import com.hasitha.back_end.billItem.BillItemDTO;
import com.hasitha.back_end.exceptions.AppException;
import com.hasitha.back_end.exceptions.MessageConstants;
import com.hasitha.back_end.response.ApiResponse;
import com.hasitha.back_end.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/bills")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillResource {

    private final BillCreateService billCreateService = new BillCreateService();
    ApiResponse apiResponse;

    /**
     * POST /bills : create new bill
     *
     * @param req
     * @return
     */
    @POST
    public Response createBill(CreateBillRequest req, @Context HttpServletRequest httpRequest) {

        User user = (User) httpRequest.getSession().getAttribute("user");

        // System.out.println(httpRequest.getSession().getAttribute("user"));
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Login required").build();
        }

        BillDTO bill = billCreateService.createBill(user.getId(), req);
        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, bill);
        return Response
                .status(Response.Status.OK)
                .entity(apiResponse)
                .build();

    }

    /**
     * GET /bills : list headers only
     *
     * @return
     */
    @GET
    public Response all() {

        List<BillDTO> bills = billCreateService.getAllBills();

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, bills);
        return Response
                .status(Response.Status.OK)
                .entity(apiResponse)
                .build();

    }

    /**
     * GET /bills/{id}/items : line items for bill
     *
     * @param billId
     * @return
     */
    @GET
    @Path("/{id}/items")
    public Response items(@PathParam("id") int billId) {

        List<BillItemDTO> billItems = billCreateService.getItemsForBill(billId);

        apiResponse = new ApiResponse(MessageConstants.SUCCESS_CODE, MessageConstants.LIST_SUCCESS, billItems);
        return Response
                .status(Response.Status.OK)
                .entity(apiResponse)
                .build();

    }
}
