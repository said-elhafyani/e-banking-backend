package org.sid.ebankingbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.dtos.*;
import org.sid.ebankingbackend.enteties.*;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.mappers.BankAccountMapperImpl;
import org.sid.ebankingbackend.repositories.AccountOperationRepository;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j // lombok annotation for logger (by SLF4j) add a log attribute to the class (private static final Logger log = LoggerFactory.getLogger(BankAccoutServiceImpl.class);)
public class BankAccoutServiceImpl implements BankAccountService{

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl bankAccountMapper;
    // logger for log messages (by slf4j)
    //Logger logger = LoggerFactory.getLogger(BankAccoutServiceImpl.class);


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = bankAccountMapper.fromCustomerDTOToCustomer(customerDTO);
        customer =customerRepository.save(customer);
        log.info("saving customer {}", customer);
        return bankAccountMapper.fromCustomerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = bankAccountMapper.fromCustomerDTOToCustomer(customerDTO);
        customer =customerRepository.save(customer);
        log.info("updating customer {}", customer);
        return bankAccountMapper.fromCustomerToCustomerDTO(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if (customer == null) {
            // exception for customer not found (exception métier)
            throw new CustomerNotFoundException("customer not found");
        }
        CurrentAccount currentAccount;
        currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setOverdraft(overDraft);
        return bankAccountMapper.fromCurrentBankAccountToCurrentBankAccountDTO(bankAccountRepository.save(currentAccount));
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerID) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if (customer == null) {
            // exception for customer not found (exception métier)
            throw new CustomerNotFoundException("customer not found");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setInterestRate(interestRate);
        return bankAccountMapper.fromSavingBankAccountToSavingBankAccountDTO(bankAccountRepository.save(savingAccount));
    }


    @Override
    public List<CustomerDTO> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer -> bankAccountMapper.fromCustomerToCustomerDTO(customer)).collect(Collectors.toList()); // map customer to customerDTO
    }

    @Override
    public CustomerDTO getCustomer(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            // exception for customer not found (exception métier)
            throw new CustomerNotFoundException("customer not found");
        }
        return bankAccountMapper.fromCustomerToCustomerDTO(customer);
    }

    @Override
    public List<BankAccountDTO> getBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts.stream().map(bankAccount -> {
            if(bankAccount instanceof CurrentAccount){
                return bankAccountMapper.fromCurrentBankAccountToCurrentBankAccountDTO((CurrentAccount) bankAccount);
            }
            return bankAccountMapper.fromSavingBankAccountToSavingBankAccountDTO((SavingAccount) bankAccount);
        }).collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getBankAccount(String accountCode) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountCode).orElseThrow(()->new BankAccountNotFoundException("bank account not found"));
        if(bankAccount instanceof CurrentAccount){
            return bankAccountMapper.fromCurrentBankAccountToCurrentBankAccountDTO((CurrentAccount) bankAccount);
        }
            return bankAccountMapper.fromSavingBankAccountToSavingBankAccountDTO((SavingAccount) bankAccount);
    }

    @Override
    public void debit(double amount, String accountCode, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountCode).orElseThrow(()->new BankAccountNotFoundException("bank account not found"));
        if(bankAccount.getBalance() < amount) {
            // exception for insufficient balance (exception metier)
            throw new BalanceNotSufficientException("insufficient balance");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(double amount, String accountCode, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountCode).orElseThrow(()->new BankAccountNotFoundException("bank account not found"));
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(double amount, String accountCode1, String accountCode2) throws BankAccountNotFoundException, BalanceNotSufficientException {
            debit(amount, accountCode1, "transfer to "+accountCode2);
            credit(amount, accountCode2, "transfer from "+accountCode1);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountCode) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountCode).orElseThrow(()->new BankAccountNotFoundException("bank account not found"));
        return bankAccount.getAccountOperations().stream().map(accountOperation -> bankAccountMapper.fromAccountOperationToAccountOperationDTO(accountOperation)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String id, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElse(null);
        if (bankAccount == null) {
            // exception for bank account not found (exception métier)
            throw new BankAccountNotFoundException("bank account not found");
        }
       Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccount_Id(id, PageRequest.of(page, size));
       AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
       List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> bankAccountMapper.fromAccountOperationToAccountOperationDTO(op)).collect(Collectors.toList());
         accountHistoryDTO.setAccountOperationsDTO(accountOperationDTOS);
         accountHistoryDTO.setAccountID(bankAccount.getId());
            accountHistoryDTO.setBalance(bankAccount.getBalance());
            accountHistoryDTO.setPageSize(size);
            accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
            accountHistoryDTO.setCurrentPage(page);
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomer(String name) {
        List<Customer> customers = customerRepository.searchCustomer(name);
            return customers.stream().map(customer -> bankAccountMapper.fromCustomerToCustomerDTO(customer)).collect(Collectors.toList());
    }


}
