package org.sid.ebankingbackend.enteties;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CC")
@Data @AllArgsConstructor @NoArgsConstructor
public class CurrentAccount extends BankAccount{
    private double overdraft;
}
