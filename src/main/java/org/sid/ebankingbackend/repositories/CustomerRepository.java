package org.sid.ebankingbackend.repositories;

import org.sid.ebankingbackend.enteties.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContains(String name);

    @Query("select c from Customer c where c.name like %:nm%")
    List<Customer> searchCustomer(@Param(value = "nm") String name);

}
