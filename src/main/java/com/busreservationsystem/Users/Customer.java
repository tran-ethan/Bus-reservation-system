package com.busreservationsystem.Users;

/**
 * The Customer class represents a customer in bus reservation system
 * with the ability to make bookings and manage balance
 * Is subclass of User.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyhronopoulos
 * @author Christopher Soussa
 */

public class Customer extends User {

    private double accountBalance;

    public Customer(String firstName, String lastName, String username, String password, String email, double startingBalance) {
        super(firstName, lastName, username, password, email);
        this.accountBalance = startingBalance;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void addToAccountBalance(double addAmount) {
        if (addAmount > 0)
            this.accountBalance += addAmount;
        else System.out.println("Invalid ammount. The amount to add has to be positive.");
    }

    @Override
    public String getType() {
        return "Customer";
    }
}
