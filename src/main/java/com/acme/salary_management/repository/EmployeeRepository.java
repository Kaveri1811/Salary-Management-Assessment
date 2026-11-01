package com.acme.salary_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.acme.salary_management.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>,
        JpaSpecificationExecutor<Employee> {

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByEmail(String email);
}