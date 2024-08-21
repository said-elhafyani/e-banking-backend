package org.sid.ebankingbackend.enteties;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    // if we have a relation between two entities and this relation bidirectional, we should use mappedBy attribute in the parent entity
    // if we don't use mappedBy attribute, we will have two tables in the database, one for each entity, and a third table that will manage the relation between the two entities
    @OneToMany(mappedBy = "customer")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // not serialize this field
    private List<BankAccount> bankAccounts;


}
