package com.queue.system.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class TicketResponse {

    private Long ticketId;
    private String queueNumber;
    private String serviceName;
    private String branchName;
    private Integer position;
    private Integer estimatedWaitMinutes;
    private String status;
    private LocalDateTime bookedAt;


    public TicketResponse(Long ticketId, String queueNumber, String serviceName,
                          String branchName, Integer position, Integer estimatedWaitMinutes,
                          String status, LocalDateTime bookedAt) {
        this.ticketId = ticketId;
        this.queueNumber = queueNumber;
        this.serviceName = serviceName;
        this.branchName = branchName;
        this.position = position;
        this.estimatedWaitMinutes = estimatedWaitMinutes;
        this.status = status;
        this.bookedAt = bookedAt;
    }

}
