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

    /**
     * Sets the full name of the user.
     *
     * @param fullName New name to be set, must contain both first and last name at least.
     * @throws IllegalArgumentException if full name is empty, or does not contain first and last name
     */
    public void setFullName(String fullName) {
        if (fullName.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        if (fullName.split(" ").length < 2) throw new IllegalArgumentException("Invalid name");
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username New username to be set. Username must be unique, no other User should have the same username.
     * @throws IllegalArgumentException if username is empty or already exists
     */
    public void setUsername(String username) throws IllegalArgumentException {
        if (username.isEmpty()) throw new IllegalArgumentException("Username cannot be empty");
        for (Client client: Database.getClients()) {
            if (client.getUsername().equals(username)) throw new IllegalArgumentException("Username is already taken");
        }
        for (Admin admin: Database.getAdmins()) {
            if (admin.getUsername().equals(username)) throw new IllegalArgumentException("Username is already taken");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) throws IllegalArgumentException {
        if (password.isEmpty()) throw new IllegalArgumentException("Password cannot be empty");
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for the user.
     *
     * @param email Email address to be set
     * @throws IllegalArgumentException if the email is null, empty, or has an invalid format
     */
    public void setEmail(String email) {
        if (email.isEmpty()) throw new IllegalArgumentException("Email cannot be empty");
        String pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(pattern)) throw new IllegalArgumentException("Invalid email format");
        this.email = email;
    }

    /**
     * Returns the type of User as a String.
     * @return Type
     */
    public abstract String getType();
}
