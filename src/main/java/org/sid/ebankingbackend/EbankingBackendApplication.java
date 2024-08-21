package org.sid.ebankingbackend;

import org.sid.ebankingbackend.enteties.AccountOperation;
import org.sid.ebankingbackend.enteties.CurrentAccount;
import org.sid.ebankingbackend.enteties.Customer;
import org.sid.ebankingbackend.enteties.SavingAccount;
import org.sid.ebankingbackend.enums.AccountStatus;
import org.sid.ebankingbackend.enums.OperationType;
import org.sid.ebankingbackend.repositories.AccountOperationRepository;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }



//    @Bean
//    CommandLineRunner start(CustomerRepository customerRepository,
//                            BankAccountRepository bankAccountRepository,
//                            AccountOperationRepository accountOperationRepository) {
//        return args -> {
//            Stream.of("Hassan", "Yassin", "Aicha").forEach(name -> {
//                Customer customer = new Customer();
//                customer.setName(name);
//                customer.setEmail(name + "@gmail.com");
//                customerRepository.save(customer);
//            });
//
//            customerRepository.findAll().forEach(customer -> {
//                CurrentAccount currentAccount = new CurrentAccount();
//                currentAccount.setId(UUID.randomUUID().toString());
//                currentAccount.setBalance(Math.random()*9000);
//                currentAccount.setCreatedAt(new Date());
//                currentAccount.setStatus(AccountStatus.CREATED);
//                currentAccount.setOverdraft(9000);
//                currentAccount.setCustomer(customer);
//                bankAccountRepository.save(currentAccount);
//
//                SavingAccount savingAccount = new SavingAccount();
//                savingAccount.setId(UUID.randomUUID().toString());
//                savingAccount.setBalance(Math.random()*9000);
//                savingAccount.setCreatedAt(new Date());
//                savingAccount.setStatus(AccountStatus.CREATED);
//                savingAccount.setInterestRate(5.5);
//                savingAccount.setCustomer(customer);
//                bankAccountRepository.save(savingAccount);
//            });
//
//            bankAccountRepository.findAll().forEach(bankAccount -> {
//                for(int i = 0; i < 10; i++) {
//                    AccountOperation accountOperation = new AccountOperation();
//                    accountOperation.setAmount(Math.random()*9000);
//                    accountOperation.setOperationDate(new Date());
//                    accountOperation.setType(Math.random() >0.5 ? OperationType.DEBIT : OperationType.CREDIT);
//                    accountOperation.setBankAccount(bankAccount);
//                    accountOperationRepository.save(accountOperation);
//                }
//            });
//        };
//    }

}
