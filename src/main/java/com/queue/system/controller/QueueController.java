package com.queue.system.controller;


import com.queue.system.dto.request.TakeTicketRequest;
import com.queue.system.dto.response.ApiResponse;
import com.queue.system.dto.response.TicketResponse;
import com.queue.system.service.QueueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queue")
@CrossOrigin(origins = "*")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService){
        this.queueService = queueService;
    }

    @PostMapping("/take-ticket")
    public ResponseEntity<ApiResponse<TicketResponse>> takeTicket(@Valid @RequestBody TakeTicketRequest request){
        try {
             TicketResponse response = queueService.takeTicket(request.getServiceId(), request.getUserId(),
                     request.getPriority());
            return ResponseEntity.ok(ApiResponse.success("Antrian berhasil diambil", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }


}
