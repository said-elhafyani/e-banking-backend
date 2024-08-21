package org.sid.ebankingbackend.enteties;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@DiscriminatorValue("SA")
@Data @AllArgsConstructor @NoArgsConstructor
public class SavingAccount extends BankAccount{
    private double interestRate;
}
