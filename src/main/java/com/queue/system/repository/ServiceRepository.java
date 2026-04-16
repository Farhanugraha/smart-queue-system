package com.queue.system.repository;

import com.queue.system.entity.Branch;
import com.queue.system.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByBranch(Branch branch);

    List<Service> findByBranchId(Long branchId);

    List<Service> findByBranchAndIsActive(Branch branch, Boolean isActive);

    List<Service> findByServiceType(String serviceType);

    Optional<Service> findByPrefix(String prefix);

    List<Service> findByIsActiveTrue();
}
