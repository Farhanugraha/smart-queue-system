package com.queue.system.repository;

import com.queue.system.entity.QueueSession;
import com.queue.system.entity.Ticket;
import com.queue.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByQueueSessionAndCalledAtIsNullOrderByPositionAsc(QueueSession session);

    Optional<Ticket> findTopByQueueSessionOrderByPositionDesc(QueueSession session);

    List<Ticket> findByUser(User user);

    List<Ticket> findByUserId(Long userId);

    List<Ticket> findByUserIdAndCalledAtIsNull(Long userId);

    Optional<Ticket> findByQueueNumber(String queueNumber);

    List<Ticket> findByCalledAtIsNotNullAndServedAtIsNull();

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.queueSession.id = :sessionId AND t.calledAt IS NULL")
    Integer countWaitingTickets(@Param("sessionId") Long sessionId);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.queueSession.id = :sessionId AND t.position < :position AND t.calledAt IS NULL")
    Integer countPositionAhead(@Param("sessionId") Long sessionId, @Param("position") Integer position);

    List<Ticket> findByBookedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT t FROM Ticket t WHERE t.user.id = :userId AND DATE(t.bookedAt) = CURRENT_DATE")
    List<Ticket> findTodayTicketsByUser(@Param("userId") Long userId);

    @Query("SELECT DATE(t.bookedAt), COUNT(t) FROM Ticket t WHERE t.queueSession.service.id = :serviceId GROUP BY DATE(t.bookedAt)")
    List<Object[]> countTicketsPerDay(@Param("serviceId") Long serviceId);

}
