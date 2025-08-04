package com.hasitha.back_end.authentication;

import com.hasitha.back_end.exceptions.ValidationException;
import com.hasitha.back_end.user.User;

public class AuthService {

    private final AuthDAO authDao;

    public AuthService(AuthDAO authDao) {
        this.authDao = authDao;
    }

    public AuthService() {
        this.authDao = new AuthDAOImpl();
    }

    public User login(LoginRequest request) {
        validateLoginRequest(request);
        String username = request.getUsername().trim();
        String password = request.getPassword().trim();
        User user = authDao.authenticate(username, password);
        if (user == null) {
            throw new ValidationException("Incorrect username or password. Please try again.");
        }
        return user;
    }

    private void validateLoginRequest(LoginRequest request) {
        if (request == null) {
            throw new ValidationException("Login request cannot be null.");
        }
        String username = request.getUsername();
        String password = request.getPassword();

        if (username == null || username.isBlank()) {
            throw new ValidationException("Username is required.");
        }
        if (username.length() < 4 || username.length() > 30) {
            throw new ValidationException("Username must be 4–30 characters.");
        }

        if (password == null || password.isBlank()) {
            throw new ValidationException("Password is required.");
        }
        if (password.length() < 6 || password.length() > 50) {
            throw new ValidationException("Password must be 6–50 characters.");
        }
    }

}
