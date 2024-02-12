package com.ticketapplication.repositories;

import com.ticketapplication.entity.device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface deviceRepository extends JpaRepository<device,Integer> {

    @Query("SELECT a FROM device a WHERE a.serialNumber LIKE %?1% ")
    List<device> findByserialNumber(String x);



    @Query("SELECT t FROM device t WHERE " +
            "t.serialNumber LIKE %?1% OR " +
            "t.deviceModel LIKE %?1% OR " +
            "t.branch LIKE %?1% OR t.comments LIKE %?1% " )
    List<device> findBySearch(String searchValue);




}
