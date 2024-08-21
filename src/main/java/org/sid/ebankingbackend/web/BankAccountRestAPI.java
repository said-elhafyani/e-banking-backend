package org.sid.ebankingbackend.web;

import org.sid.ebankingbackend.dtos.AccountHistoryDTO;
import org.sid.ebankingbackend.dtos.AccountOperationDTO;
import org.sid.ebankingbackend.dtos.BankAccountDTO;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> getBankAccounts() {
        return bankAccountService.getBankAccounts();
    }

    @GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable  String id)  throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(id);
    }

    @GetMapping("/accounts/{id}/operations")
    public List<AccountOperationDTO> accountHistory(@PathVariable String id) throws BankAccountNotFoundException {
        return bankAccountService.accountHistory(id);
    }

    @GetMapping("/accounts/{id}/pageOperations")
    public AccountHistoryDTO accountHistory(@PathVariable String id,
                                                  @RequestParam(name = "page",defaultValue = "0") int page,
                                                  @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(id,page,size);
    }





}
