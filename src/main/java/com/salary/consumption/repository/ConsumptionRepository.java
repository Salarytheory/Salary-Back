package com.salary.consumption.repository;

import com.salary.consumption.entity.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ConsumptionRepository  extends JpaRepository<Consumption, Long> {
}
