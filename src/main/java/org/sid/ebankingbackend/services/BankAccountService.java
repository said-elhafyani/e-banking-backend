package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.dtos.*;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long id);

    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerID) throws CustomerNotFoundException;
    List<CustomerDTO> getCustomers();
    CustomerDTO getCustomer(Long id) throws CustomerNotFoundException;
    List<BankAccountDTO> getBankAccounts();
    BankAccountDTO getBankAccount(String accountCode) throws BankAccountNotFoundException;
    DebitDTO debit(double amount, String accountCode, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    DebitDTO credit(double amount, String accountCode, String description) throws BankAccountNotFoundException;
    void transfer(double amount, String accountCode1, String accountCode2) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<AccountOperationDTO> accountHistory(String accountCode) throws BankAccountNotFoundException;

    AccountHistoryDTO getAccountHistory(String id, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomer(String name);
}
