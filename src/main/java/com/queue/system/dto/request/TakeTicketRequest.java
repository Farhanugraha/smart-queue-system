package com.queue.system.dto.request;

import jakarta.validation.constraints.NotNull;

public class TakeTicketRequest {

    @NotNull(message = "Service Id is required!")
    private Long serviceId;

    @NotNull(message = "User Id is required!")
    private Long userId;

    private String priority;

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId;}

    public Long getUserId() { return  userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

}
