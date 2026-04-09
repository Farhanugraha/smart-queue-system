package com.queue.system.repository;

import com.queue.system.entity.Notification;
import com.queue.system.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {


    List<Notification> findByUser(User user);

    List<Notification> findByUserIdOrderBySentDesc(Long userId);

    List<Notification> findByUserAndIsReadFalse(Long userId);

    List<Notification> findByType(String type);

    List<Notification> findBySentAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(n) FROM NOTIFICATION n WHERE n.user.id = :userId AND n.isRead = false")
    void markAllAsRead(@Param("userId") Long userId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Notification n WHERE n.sentAt < :cutoffDate")
    void deleteOldNotification(@Param("cutoffDate") LocalDateTime cutoffDate);

}
