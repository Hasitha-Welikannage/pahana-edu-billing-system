package com.hasitha.back_end.exceptions;

public class MessageConstants {

    // ✅ Success Codes & Messages
    public static final String SUCCESS_CODE = "200";
    public static final String CREATE_SUCCESS = "Record created successfully";
    public static final String READ_SUCCESS = "Record fetched successfully";
    public static final String UPDATE_SUCCESS = "Record updated successfully";
    public static final String DELETE_SUCCESS = "Record deleted successfully";
    public static final String LIST_SUCCESS = "Records retrieved successfully";
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String LOGOUT_SUCCESS = "Logout successful";
    public static final String REGISTER_SUCCESS = "User registered successfully";
    public static final String BILL_CREATE_SUCCESS = "Bill created successfully";

    // ❌ Validation Error Codes (starts with 4xx)
    public static final String VALIDATION_ERROR_CODE = "400";
    public static final String FIELD_REQUIRED = "Field '%s' is required";
    public static final String INVALID_FORMAT = "Field '%s' is invalid";
    public static final String PASSWORD_TOO_SHORT = "Password must be at least 6 characters";
    public static final String EMAIL_EXISTS = "Email is already in use";
    public static final String USERNAME_EXISTS = "Username is already taken";

    // 🔐 Auth Errors (401–403)
    public static final String UNAUTHORIZED_CODE = "401";
    public static final String FORBIDDEN_CODE = "403";
    public static final String INVALID_CREDENTIALS = "Invalid username or password";
    public static final String UNAUTHORIZED_ACCESS = "You are not authorized to perform this action";
    public static final String FORBIDDEN_ROLE = "Access denied for current role";

    // ⚙️ Business Logic Errors (404, 409, etc.)
    public static final String NOT_FOUND_CODE = "404";
    public static final String DUPLICATE_CODE = "409";
    public static final String RECORD_NOT_FOUND = "Requested record not found";
    public static final String DUPLICATE_RECORD = "Record already exists";
    public static final String CANNOT_DELETE_REFERENCED = "Cannot delete this item; it is referenced elsewhere";
    public static final String ACTION_NOT_ALLOWED = "This action is not allowed";

    // ⚠️ Server Errors
    public static final String SERVER_ERROR_CODE = "500";
    public static final String INTERNAL_ERROR = "An unexpected error occurred";
    public static final String DATABASE_ERROR = "A database error occurred";
    public static final String TIMEOUT_ERROR = "Request timed out, please try again";

    private MessageConstants() {
    }
}
