package com.hasitha.back_end.authentication;

import com.hasitha.back_end.exceptions.ValidationException;
import com.hasitha.back_end.user.User;

public class AuthService {

    private final AuthenticationDAO authDao = new AuthenticationDAOImpl();

    public User login(LoginRequest request) {

        if (request.getUsername() == null || request.getUsername().equalsIgnoreCase("")) {
            throw new ValidationException("Username can not be empty");
        }

        if (request.getPassword() == null || request.getPassword().equalsIgnoreCase("")) {
            throw new ValidationException("Password can not be empty");
        }

        return authDao.authenticate(request.getUsername(), request.getPassword());

    }
}
