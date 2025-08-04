package com.hasitha.back_end.exceptions;

import com.hasitha.back_end.response.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Exception mapper that handles {@link NotFoundException} and converts it into
 * a standardized HTTP 404 Not Found response.
 *
 * This class is annotated with {@link Provider} to register it as a JAX-RS
 * exception mapper. When a NotFoundException is thrown, this mapper creates an
 * error response with the appropriate status and message.
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    /**
     * Converts a {@link NotFoundException} into a JAX-RS {@link Response}.
     *
     * @param e the caught NotFoundException
     * @return a Response object with HTTP status 404 and a JSON error body
     */
    @Override
    public Response toResponse(NotFoundException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(MessageConstants.NOT_FOUND_CODE, e.getMessage(), null))
                .build();
    }
}
