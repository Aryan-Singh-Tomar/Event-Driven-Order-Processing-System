package com.orderflow.notificationservice.repository;

import com.orderflow.notificationservice.entity.NotificationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationRecord, Long> {

    boolean existsByOrderId(Long orderId);
}
