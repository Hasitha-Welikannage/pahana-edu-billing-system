package com.hasitha.back_end.authentication;

import com.hasitha.back_end.user.User;

/**
 * Data Access Object for authentication-related operations.
 */
public interface AuthDAO {

    /**
     * Authenticates a user with username and password.
     *
     * @param username the username to authenticate
     * @param password the password to verify
     * @return User if authentication successful, null otherwise
     * @throws DatabaseException if a database access error occurs
     */
    User authenticate(String username, String password);
}
