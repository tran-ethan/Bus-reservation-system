package com.busreservationsystem.Users;

/**
 * The User class represents a user entity in bus reservation system.
 * Is superclass of Admin and Customer.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyhronopoulos
 * @author Christopher Soussa
 */

public abstract class User {

    private String firstName, lastName, username, password, email;

    /**
     * Constructor for the User class.
     *
     * @param firstName First name of the user
     * @param lastName Last name of user
     * @param username Username of user
     * @param password Password of user
     * @param email Email address of user
     */
    public User(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
    abstract String getType();
}
