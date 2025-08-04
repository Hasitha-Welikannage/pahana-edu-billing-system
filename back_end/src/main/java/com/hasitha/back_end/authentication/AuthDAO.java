package com.hasitha.back_end.authentication;

import com.hasitha.back_end.user.User;

/**
 * Data Access Object (DAO) interface for authentication-related database
 * operations. Provides method(s) to verify user credentials.
 */
public interface AuthDAO {

    /**
     * Attempts to authenticate a user based on provided username and password.
     *
     * @param username the username of the user attempting to log in
     * @param password the plain-text password provided by the user
     * @return a {@link User} object if authentication is successful; otherwise,
     * returns {@code null}
     * @throws com.hasitha.back_end.exceptions.DatabaseException if a database
     * access error occurs
     */
    User authenticate(String username, String password);
}
