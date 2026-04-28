package com.queue.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "queue_session")
public class QueueSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(name = "session_date")
    private LocalDate localDate;

    @Column(name = "current_number")
    private Integer currentNumber;

    @Column(name = "total_called")
    private Integer totalCalled;

    @Enumerated(EnumType.STRING)
    private QueueSessionStatus status;


}
