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

    Optional<QueueSession> findByServiceAndSessionDate(Service service, LocalDate date);

    Optional<QueueSession> findByServiceIdAndSessionDate(Long serviceId, LocalDate date);

    List<QueueSession> findBySessionAndStatus(LocalDate date, String status);

    List<QueueSession> findByServiceAndStatus(Service service, String status);

    @Query("SELECT COUNT(q) FROM QueueSession q WHERE q.sessionDate = CURRENT_DATE AND q.status = 'ACTIVE'")
    Long countActiveSessionsToday();

    List<QueueSession> findBySessionDateBeforeAndStatus(LocalDate date, String status);














}
