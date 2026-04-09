package com.queue.system.repository;

import com.queue.system.entity.Counter;
import com.queue.system.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {

    List<Counter> findByService(Service service);

    List<Counter> findByServiceId(Long serviceId);

    List<Counter> findByStatus(String status);

    List<Counter> findByServiceAndStatus(Service service, String status);

    List<Counter> findByOfficerId(Long officerId);

    Optional<Counter> findByCounterName(String counterName);

    @Query("SELECT c FROM Counter c WHERE c.service.branch.id = :branchId AND c.status = 'OPEN'")
    List<Counter> findOpenCountersByBranchId(@Param("branchId") Long branchId);


}
