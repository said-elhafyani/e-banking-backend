package org.sid.ebankingbackend.enteties;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebankingbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.SINGLE_TABLE) // This strategy creates a table for the superclass and subclasses with a discriminator column
//@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED) // This strategy creates a table for the superclass and a table for each subclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // This strategy creates a table for each subclass and the superclass table is empty (abstract class for delete the table from the database)
@DiscriminatorColumn(name = "TYPE", length = 4)
@Data @AllArgsConstructor @NoArgsConstructor
public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount") // default fetch type is LAZY
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // not serialize this field
    private List<AccountOperation> accountOperations;
}
