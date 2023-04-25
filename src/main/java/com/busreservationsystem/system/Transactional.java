package com.busreservationsystem.system;

public interface Transactional {

    void deposit(double amount);
    void withdraw(double amount);
    double getBalance();

}
