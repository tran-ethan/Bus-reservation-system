package com.busreservationsystem.system;

/**
 * The Customer class represents a customer in bus reservation system
 * with the ability to search and book flights.
 * Is subclass of User.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyhronopoulos
 * @author Christopher Soussa
 */

public class Client extends User implements Transactional {

    private double balance;

    public Client(String fullName, String username, String password, String email, double startingBalance) {
        super(fullName, username, password, email);
        this.balance = startingBalance;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) this.balance += amount;
        else System.out.println("Invalid amount (must be positive).");
    }

    @Override
    public void withdraw(double amount) {

    }

    @Override
    public double getBalance() {
        return 0;
    }

    public double getAccountBalance() {
        return balance;
    }

    public void setAccountBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String getType() {
        return "Client";
    }
}
