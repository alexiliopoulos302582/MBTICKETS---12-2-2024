package com.ticketapplication.repositories;

import com.ticketapplication.entity.aCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface aCustomerRepository extends JpaRepository<aCustomer,Integer> {



    @Query("SELECT a FROM aCustomer a WHERE a.aCustomerName LIKE %?1% ")
    List<aCustomer> findAllByaCustomerName(String x);



    @Query("SELECT a FROM aCustomer a WHERE a.aCustomerPhoneNumber LIKE %?1% ")
    List<aCustomer> findAllByPhoneNumber(String phone);



    @Query("SELECT t FROM aCustomer t WHERE " +
            "t.aCustomerPhoneNumber LIKE %?1% OR " +
            "t.aCustomerCity LIKE %?1% OR " +
            "t.aCustomerName LIKE %?1% OR " +
            "t.aCustomerEmail LIKE %?1% OR " +
            "t.aCustomerId LIKE %?1% OR " +
            "t.aCustomerafm LIKE %?1% OR " +
            "t.aCustomerAddress LIKE %?1% ")
        List<aCustomer> findBranches(String y);
}
