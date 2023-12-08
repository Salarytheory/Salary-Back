package com.salary.consumption.repository;

import com.salary.consumption.entity.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumptionRepository  extends JpaRepository<Consumption, Long> {
}
