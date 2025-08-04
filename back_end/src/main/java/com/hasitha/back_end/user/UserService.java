package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.MessageConstants;
import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import java.util.List;

/**
 * Service layer for managing users. Handles validation and business logic
 * before delegating to the UserDAO.
 */
public class UserService {

    private final UserDAO userDao;

    // Constructor for injection (used in tests)
    public UserService(UserDAO userDAO) {
        this.userDao = userDAO;
    }

    // Default constructor (used in production)
    public UserService() {
        this.userDao = new UserDAOImpl(); // default real DAO
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of users
     * @throws NotFoundException if no users are found
     */
    public List<User> findAll() {
        List<User> userList = userDao.findAll();
        if (userList == null || userList.isEmpty()) {
            throw new NotFoundException("No users found in the system.");
        }
        return userList;
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user
     * @return the user with the given ID
     * @throws NotFoundException if the user is not found
     */
    public User findById(int id) {
        User user = userDao.findById(id);
        if (user == null) {
            throw new NotFoundException("User with the specified ID " + id + " does not exist.");
        }
        return user;
    }

    /**
     * Creates a new user after validating the input.
     *
     * @param user the user to create
     * @return the created user
     * @throws ValidationException if input data is invalid
     */
    public User create(User user) {
        validateUser(user, true);
        return userDao.create(user);
    }

    /**
     * Updates an existing user by ID after validation.
     *
     * @param id the ID of the user to update
     * @param userUpdate the updated user data
     * @return the updated user
     * @throws NotFoundException if the user is not found
     * @throws ValidationException if the update data is invalid
     */
    public User update(int id, User userUpdate) {
        ensureUserExists(id);
        String currentPassword = userDao.findPasswordById(id);
        if (userUpdate.getPassword() == null || userUpdate.getPassword().isBlank()) {
            userUpdate.setPassword(currentPassword);
        }
        userUpdate.setId(id);
        validateUser(userUpdate, false);
        return userDao.update(id, userUpdate);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @throws NotFoundException if the user is not found
     */
    public void delete(int id) {
        ensureUserExists(id);
        userDao.delete(id);
    }

    /**
     * Checks if a user exists by ID.
     *
     * @param id the ID to check
     * @return true if user ensureUserExists, false otherwise
     */
    public void ensureUserExists(int id) {
        if (userDao.findById(id) == null) {
            throw new NotFoundException("User with the specified ID " + id + " does not exist.");
        }
    }

    /**
     * Validates the fields of a User object.
     *
     * @param user the user to validate
     * @param isCreate true if creating a new user; false if updating
     * @throws ValidationException if any field is invalid or username is
     * duplicated
     */
    private void validateUser(User user, boolean isCreate) {
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            throw new ValidationException("First name is required and cannot be empty.");
        }

        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new ValidationException("Last name is required and cannot be empty.");
        }

        if (user.getUserName() == null || user.getUserName().isBlank()) {
            throw new ValidationException("Username is required and cannot be empty.");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new ValidationException("Password is required and cannot be empty.");
        }

        if (user.getRole() == null || user.getRole().isBlank()) {
            throw new ValidationException("User role is required and cannot be empty.");
        }

        if (isCreate) {
            if (userDao.findByUsername(user.getUserName()) != null) {
                throw new ValidationException(MessageConstants.USERNAME_EXISTS);
            }
        } else {

            // Allow same username if it belongs to the same user (update scenario)
            User existingUser = userDao.findByUsername(user.getUserName());
            if (existingUser != null && existingUser.getId() != user.getId()) {
                throw new ValidationException(MessageConstants.USERNAME_EXISTS);
            }
        }
    }
}
