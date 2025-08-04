package com.hasitha.back_end.exceptions;

import com.hasitha.back_end.response.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Generic exception mapper that handles all uncaught exceptions (Throwable).
 *
 * This class is annotated with {@link Provider} to register it as a JAX-RS
 * exception mapper. It catches any unexpected exceptions that are not
 * explicitly handled elsewhere and returns a standardized error response with
 * HTTP status 500 (Internal Server Error).
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    /**
     * Converts a generic Throwable into a JAX-RS {@link Response}.
     *
     * @param e the caught exception or error
     * @return a Response object with status 500 and a JSON error body
     */
    @Override
    public Response toResponse(Throwable e) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(MessageConstants.SERVER_ERROR_CODE, e.getMessage(), null))
                .build();
    }
}
