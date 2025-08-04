package com.hasitha.back_end.exceptions;

import com.hasitha.back_end.response.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Exception mapper that converts {@link DatabaseException} into a standardized
 * HTTP response.
 *
 * This class is annotated with {@link Provider} to register it as a JAX-RS
 * exception mapper. When a DatabaseException is thrown, this mapper catches it
 * and returns a JSON error response with HTTP status 500 (Internal Server
 * Error).
 *
 */
@Provider
public class DatabaseExceptionMapper implements ExceptionMapper<DatabaseException> {

    /**
     * Converts a {@link DatabaseException} into a JAX-RS {@link Response}.
     *
     * @param e the caught DatabaseException
     * @return a Response object with status 500 and a JSON error body
     */
    @Override
    public Response toResponse(DatabaseException e) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(MessageConstants.SERVER_ERROR_CODE, e.getMessage(), null))
                .build();
    }
}
