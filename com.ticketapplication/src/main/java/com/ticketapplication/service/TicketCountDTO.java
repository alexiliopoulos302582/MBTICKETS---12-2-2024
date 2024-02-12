package com.ticketapplication.service;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "user_ticket_counts")
public class TicketCountDTO {


    @Id
    private String assignedTo;
    private Long openTicketCount;
    private Long closedTicketCount;


    public TicketCountDTO() {
    }

    public TicketCountDTO(String assignedTo, Long openTicketCount, Long closedTicketCount) {
        this.assignedTo = assignedTo;
        this.openTicketCount = openTicketCount;
        this.closedTicketCount = closedTicketCount;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Long getOpenTicketCount() {
        return openTicketCount;
    }

    public void setOpenTicketCount(Long openTicketCount) {
        this.openTicketCount = openTicketCount;
    }

    public Long getClosedTicketCount() {
        return closedTicketCount;
    }

    public void setClosedTicketCount(Long closedTicketCount) {
        this.closedTicketCount = closedTicketCount;
    }
}
