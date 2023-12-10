package com.salary.plan.repository;

import com.salary.plan.entity.GoalManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalManagementRepository extends JpaRepository<GoalManagement, Long> {
}
