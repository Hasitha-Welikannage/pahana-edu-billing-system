package com.hasitha.back_end.exceptions;

import com.hasitha.back_end.response.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Exception mapper that handles {@link ValidationException} and converts it
 * into a standardized HTTP 400 Bad Request response.
 *
 * This class is annotated with {@link Provider} to register it as a JAX-RS
 * exception mapper. When a ValidationException is thrown, this mapper creates
 * an error response with the appropriate status and message.
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    /**
     * Converts a {@link ValidationException} into a JAX-RS {@link Response}.
     *
     * @param e the caught ValidationException
     * @return a Response object with HTTP status 400 and a JSON error body
     */
    @Override
    public Response toResponse(ValidationException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(MessageConstants.VALIDATION_ERROR_CODE, e.getMessage(), null))
                .build();
    }
}
