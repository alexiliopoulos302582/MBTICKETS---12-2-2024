package com.ticketapplication.repositories;

import com.ticketapplication.entity.ticket;
import com.ticketapplication.service.TicketCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ticketRepository extends JpaRepository<ticket,Long> {


    @Query(value = "SELECT t FROM ticket t WHERE t.ticketState = 0 ORDER BY t.creationDate DESC")
    List<ticket> findAllClosedTickets();

    @Query(value = "SELECT t FROM ticket t WHERE t.ticketState = 1 ORDER BY t.creationDate DESC")
    List<ticket> findAllOpenTickets();


    @Query("SELECT t FROM ticket t WHERE " +
            "(t.phoneNumber LIKE %?1% OR " +
            "t.city LIKE %?1% OR " +
            "t.clientName LIKE %?1% OR " +
            "t.email LIKE %?1% OR " +
            "t.serialNumber LIKE %?1% OR " +
            "t.address LIKE %?1% OR " +
            "t.subject LIKE %?1% OR " +
            "t.deviceModel LIKE %?1% OR " +
            "t.category LIKE %?1% OR " +
            "t.issue LIKE %?1% OR " +
            "t.solution LIKE %?1% OR " +
            "t.solutionType LIKE %?1%) AND t.ticketState = 0 " +
            "ORDER BY t.creationDate DESC")
    List<ticket> findClosedTicketsBySearch(String searchValue);


    @Query("SELECT t FROM ticket t WHERE " +
            "(t.phoneNumber LIKE %?1% OR " +
            "t.city LIKE %?1% OR " +
            "t.clientName LIKE %?1% OR " +
            "t.email LIKE %?1% OR " +
            "t.serialNumber LIKE %?1% OR " +
            "t.address LIKE %?1% OR " +
            "t.subject LIKE %?1% OR " +
            "t.deviceModel LIKE %?1% OR " +
            "t.category LIKE %?1% OR " +
            "t.issue LIKE %?1% OR " +
            "t.solution LIKE %?1% OR " +
            "t.solutionType LIKE %?1%) AND t.ticketState = 1 " +
            "ORDER BY t.creationDate DESC")
    List<ticket> findOpenTicketsBySearch(String searchValue);





    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE user_ticket_counts", nativeQuery = true)
    void truncateUserTicketCounts();


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_ticket_counts (assigned_to, open_ticket_count, closed_ticket_count) " +
            "SELECT assigned_to, " +
            "SUM(CASE WHEN ticket_state = 1 THEN 1 ELSE 0 END) AS open_ticket_count, " +
            "SUM(CASE WHEN ticket_state = 0 THEN 1 ELSE 0 END) AS closed_ticket_count " +
            "FROM ticket " +
            "GROUP BY assigned_to", nativeQuery = true)
    void updateUserTicketCounts();


    @Query("SELECT t FROM TicketCountDTO t")
    List<TicketCountDTO> getUserTicketCountsFromTable();




}
