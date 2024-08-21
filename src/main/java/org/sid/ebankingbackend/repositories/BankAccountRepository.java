package org.sid.ebankingbackend.repositories;

import org.sid.ebankingbackend.enteties.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
