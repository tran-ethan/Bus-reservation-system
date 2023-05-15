package com.busreservationsystem.system;

/**
 * The Customer class represents a customer in bus reservation system
 * with the ability to search and book seats on a particular bus.
 * Is subclass of User.
 *
 * @author Nikolaos Polyhronopoulos
 * @author Christopher Soussa
 */

public class Client extends User implements Transactional {

    public double balance;

    public Client() {}

    public Client(String fullName, String username, String password, String email) {
        this(fullName, username, password, email, 0.0);
    }
    public Client(String fullName, String username, String password, String email, double startingBalance) {
        super(fullName, username, password, email);
        this.balance = startingBalance;
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Invalid deposit amount");
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        if (balance >= amount) this.balance -= amount;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) throws IllegalArgumentException {
        if (balance < 0) throw new IllegalArgumentException("Invalid balance");
        this.balance = balance;
    }

    @Override
    public String getType() {
        return "Client";
    }

    @Override
    public String toString() {
        return "Client{" +
                "balance=" + balance +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
