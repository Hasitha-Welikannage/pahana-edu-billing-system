package com.hasitha.back_end.user;

import java.util.List;

/**
 * Interface defining data access operations for User entities.
 */
public interface UserDAO {

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all users
     */
    List<User> findAll();

    /**
     * Finds a user by their unique ID.
     *
     * @param id the ID of the user to find
     * @return the user with the given ID, or null if not found
     */
    User findById(int id);

    /**
     * Creates a new user in the database.
     *
     * @param user the user object to create
     * @return the created user with generated ID (if applicable)
     */
    User create(User user);

    /**
     * Updates an existing user's information.
     *
     * @param id the ID of the user to update
     * @param user the updated user information
     * @return the updated user object
     */
    User update(int id, User user);

    /**
     * Deletes a user from the database.
     *
     * @param id the ID of the user to delete
     */
    void delete(int id);

    /**
     * Finds a user by their unique username.
     *
     * @param username the username to search for
     * @return the user with the specified username, or null if not found
     */
    User findByUsername(String username);
}
