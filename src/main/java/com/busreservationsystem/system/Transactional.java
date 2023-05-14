package com.busreservationsystem.system;


/**
 * The Transactional interface represents a transactional account.
 * It provides methods for depositing, withdrawing, and getting the balance of the account.
 *
 * @author Christopher Soussa
 */
public interface Transactional {

    void deposit(double amount);
    void withdraw(double amount);
    double getBalance();

}
