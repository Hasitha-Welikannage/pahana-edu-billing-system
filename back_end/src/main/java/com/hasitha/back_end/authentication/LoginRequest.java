package com.hasitha.back_end.authentication;

/**
 * A simple POJO (Plain Old Java Object) that represents a login request
 * payload. It is typically used to map JSON login request bodies in REST APIs.
 */
public class LoginRequest {

    private String username;
    private String password;

    /**
     * Returns the username from the login request.
     *
     * @return username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the login request.
     *
     * @param username the user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password from the login request.
     *
     * @return password as a String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the login request.
     *
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
