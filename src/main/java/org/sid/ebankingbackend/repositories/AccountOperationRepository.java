package org.sid.ebankingbackend.repositories;

import org.sid.ebankingbackend.enteties.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, String> {
    List<AccountOperation> findByBankAccount_Id(String accountId);
    Page<AccountOperation> findByBankAccount_IdOrderByOperationDateDesc(String accountId, Pageable pageable);

}
