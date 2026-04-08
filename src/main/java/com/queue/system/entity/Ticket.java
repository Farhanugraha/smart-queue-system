package com.queue.system.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "queue_session_id")
    private QueueSession queueSession;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "counter_id")
    private Counter counter;

    @Column(name = "queue_number")
    private String queueNumber;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private Integer position;

    @Column(name = "booked_at")
    private LocalDateTime bookedAt;

    @Column(name = "called_at")
    private LocalDateTime calledAt;

    @Column(name = "served_at")
    private LocalDateTime servedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

}
