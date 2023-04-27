package com.busreservationsystem.system;

/**
 * The User class represents a user entity in bus reservation system.
 * Is superclass of Admin and Customer.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyhronopoulos
 * @author Christopher Soussa
 */

public abstract class User {

    protected String fullName, username, password, email;

    public User() {}

    /**
     * Constructor for the User class.
     *
     * @param fullName First and last name of user.
     * @param username Username and unique identifier of user.
     * @param password Password of user.
     * @param email Email address of user.
     */
    public User(String fullName, String username, String password, String email) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the type of User as a String.
     * @return Type
     */
    public abstract String getType();
}
