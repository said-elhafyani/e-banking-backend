package org.sid.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDTO {
    private String accountID;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<AccountOperationDTO> accountOperationsDTO;
}
