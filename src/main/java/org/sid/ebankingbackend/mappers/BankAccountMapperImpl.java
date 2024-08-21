package org.sid.ebankingbackend.mappers;

import org.sid.ebankingbackend.dtos.AccountOperationDTO;
import org.sid.ebankingbackend.dtos.CurrentBankAccountDTO;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.dtos.SavingBankAccountDTO;
import org.sid.ebankingbackend.enteties.AccountOperation;
import org.sid.ebankingbackend.enteties.CurrentAccount;
import org.sid.ebankingbackend.enteties.Customer;
import org.sid.ebankingbackend.enteties.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

//MapStruct is a code generator that greatly simplifies the implementation of mappings between Java bean types based on a convention over configuration approach.
@Service
public class BankAccountMapperImpl {

    public CustomerDTO fromCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO); // copy all properties from customer to customerDTO
        return customerDTO;
    }

    public Customer fromCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer); // copy all properties from customerDTO to customer
        return customer;
    }

    public SavingBankAccountDTO fromSavingBankAccountToSavingBankAccountDTO(SavingAccount savingAccount) {
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingBankAccountDTO); // copy all properties from savingBankAccount to savingBankAccountDTO
        savingBankAccountDTO.setCustomerDTO(fromCustomerToCustomerDTO(savingAccount.getCustomer())); // set customerDTO to savingBankAccountDTO
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName()); // set type to savingBankAccountDTO
        return savingBankAccountDTO;
    }

    public SavingAccount fromSavingBankAccountDTOToSavingBankAccount(SavingBankAccountDTO savingBankAccountDTO) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO, savingAccount); // copy all properties from savingBankAccountDTO to savingAccount
        savingAccount.setCustomer(fromCustomerDTOToCustomer(savingBankAccountDTO.getCustomerDTO())); // set customer to savingAccount
        return savingAccount;
    }

    public CurrentBankAccountDTO fromCurrentBankAccountToCurrentBankAccountDTO(CurrentAccount currentAccount) {
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentBankAccountDTO); // copy all properties from currentAccount to currentBankAccountDTO
        currentBankAccountDTO.setCustomerDTO(fromCustomerToCustomerDTO(currentAccount.getCustomer())); // set customerDTO to currentBankAccountDTO
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName()); // set type to currentBankAccountDTO
        return currentBankAccountDTO;
    }

    public CurrentAccount fromCurrentBankAccountDTOToCurrentBankAccount(CurrentBankAccountDTO currentBankAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO, currentAccount); // copy all properties from currentBankAccountDTO to currentAccount
        currentAccount.setCustomer(fromCustomerDTOToCustomer(currentBankAccountDTO.getCustomerDTO())); // set customer to currentAccount
        return currentAccount;
    }

    public AccountOperationDTO fromAccountOperationToAccountOperationDTO(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO); // copy all properties from accountOperation to accountOperationDTO
        return accountOperationDTO;
    }

    public AccountOperation fromAccountOperationDTOToAccountOperation(AccountOperationDTO accountOperationDTO) {
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO, accountOperation); // copy all properties from accountOperationDTO to accountOperation
        return accountOperation;
    }
}
