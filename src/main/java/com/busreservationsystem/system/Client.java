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

    private double accountBalance;

    public Client(String fullName, String username, String password, String email, double startingBalance) {
        super(fullName, username, password, email);
        this.accountBalance = startingBalance;
    }

    @Override
    public void deposit(double amount) {

    }

    @Override
    public void withdraw(double amount) {

    }

    @Override
    public double getBalance() {
        return 0;
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
        return "Client";
    }
}
