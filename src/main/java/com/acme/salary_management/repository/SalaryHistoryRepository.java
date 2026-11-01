package com.acme.salary_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.salary_management.entity.SalaryHistory;

public interface SalaryHistoryRepository
        extends JpaRepository<SalaryHistory, Long> {

    List<SalaryHistory> findByEmployeeIdOrderByEffectiveDateDesc(Long employeeId);
}