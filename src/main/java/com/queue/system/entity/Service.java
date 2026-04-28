package com.queue.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "service")

public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private  ServiceType serviceType;

    private String prefix;

    @Column(name = "max_queue_per_day")
    private Integer maxQueuePerDay;

    @Column(name = "avg_service_minutes")
    private Integer avgServiceMinutes;

    @Column(name = "is_active")
    private Boolean isActive;

}
