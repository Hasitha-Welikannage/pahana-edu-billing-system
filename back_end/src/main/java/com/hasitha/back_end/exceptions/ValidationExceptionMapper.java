/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.exceptions;

import com.hasitha.back_end.response.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author hasithawelikannage
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException e) {
        ErrorResponse error = new ErrorResponse(MessageConstants.VALIDATION_ERROR_CODE, e.getMessage(), null);
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}
