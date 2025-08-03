package com.hasitha.back_end.user;

/**
 * Represents a User entity with properties such as id, first name, last name,
 * username, password, and role.
 */
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String role;

    /**
     * Default no-argument constructor.
     */
    public User() {
    }

    /**
     * Constructs a User with all fields.
     *
     * @param id the user ID
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param userName the username
     * @param password the password
     * @param role the user role
     */
    public User(int id, String firstName, String lastName, String userName, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructs a User without the password (used when password should be
     * hidden).
     *
     * @param id the user ID
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param userName the username
     * @param role the user role
     */
    public User(int id, String firstName, String lastName, String userName, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.role = role;
    }

    /**
     * Returns the user ID.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user ID.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the username.
     *
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username.
     *
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the user's password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the role of the user.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the User object.
     * <p>
     * Includes all fields. Be cautious with password exposure.
     *
     * @return string representation of the User
     */
    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", userName=" + userName
                + ", password=" + password
                + ", role=" + role
                + '}';
    }
}
