package com.queue.system.repository;

import com.queue.system.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    List<Branch> findByIsActiveTrue();

    Optional<Branch> findByName(String name);

    List<Branch> findByLocationContainingIgnoreCase(String location);

    List<Branch> findByIsActiveTrueAndLocation(String location);


}
