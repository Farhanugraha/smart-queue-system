package com.queue.system.repository;


import com.queue.system.entity.QueueSession;
import com.queue.system.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface QueSessionRepository extends JpaRepository<QueueSession, Long> {

    Optional<QueueSession> findByServiceAndLocalDate(Service service, LocalDate date);

    Optional<QueueSession> findByServiceIdAndLocalDate(Long serviceId, LocalDate date);

    List<QueueSession> findByLocalDateAndStatus(LocalDate date, String status);

    List<QueueSession> findByServiceAndStatus(Service service, String status);

    @Query("SELECT COUNT(q) FROM QueueSession q WHERE q.localDate = CURRENT_DATE AND q.status = 'ACTIVE'")
    Long countActiveSessionsToday();

    List<QueueSession> findByLocalDateBeforeAndStatus(LocalDate date, String status);














}
