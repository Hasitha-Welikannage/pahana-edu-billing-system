package com.hasitha.back_end.authentication;

import com.hasitha.back_end.exceptions.ValidationException;
import com.hasitha.back_end.user.User;

/**
 * Service class responsible for handling user authentication logic.
 */
public class AuthService {

    private final AuthDAO authDao;

    /**
     * Constructor with dependency injection.
     *
     * @param authDao an implementation of AuthDAO
     */
    public AuthService(AuthDAO authDao) {
        this.authDao = authDao;
    }

    /**
     * Default constructor that uses the default AuthDAOImpl.
     */
    public AuthService() {
        this.authDao = new AuthDAOImpl();
    }

    /**
     * Attempts to authenticate a user with the provided login credentials.
     *
     * @param request the login request containing username and password
     * @return the authenticated {@link User} if successful
     * @throws ValidationException if validation fails or authentication is
     * unsuccessful
     */
    public User login(LoginRequest request) {
        // Step 1: Validate login input
        validateLoginRequest(request);

        // Step 2: Clean input data
        String username = request.getUsername().trim();
        String password = request.getPassword().trim();

        // Step 3: Attempt to authenticate the user via DAO
        User user = authDao.authenticate(username, password);

        // Step 4: Handle invalid credentials
        if (user == null) {
            throw new ValidationException("Incorrect username or password. Please try again.");
        }

        // Step 5: Return authenticated user
        return user;
    }

    /**
     * Validates the login request fields.
     *
     * @param request the login request
     * @throws ValidationException if username or password is missing or invalid
     */
    private void validateLoginRequest(LoginRequest request) {
        if (request == null) {
            throw new ValidationException("Login request cannot be null.");
        }

        String username = request.getUsername();
        String password = request.getPassword();

        if (username == null || username.isBlank()) {
            throw new ValidationException("Username is required.");
        }

        // Uncomment for stricter validation
//        if (username.length() < 4 || username.length() > 30) {
//            throw new ValidationException("Username must be 4–30 characters.");
//        }
        if (password == null || password.isBlank()) {
            throw new ValidationException("Password is required.");
        }

        // Uncomment for stricter validation
//        if (password.length() < 6 || password.length() > 50) {
//            throw new ValidationException("Password must be 6–50 characters.");
//        }
    }
}
