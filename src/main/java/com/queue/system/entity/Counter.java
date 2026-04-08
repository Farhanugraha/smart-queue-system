package com.queue.system.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "counter")

public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(name = "counter_name")
    private String counterName;


    @ManyToOne
    @JoinColumn(name = "officer_id")
    private User officer;

    @Enumerated(EnumType.STRING)
    private CounterStatus status;

}
