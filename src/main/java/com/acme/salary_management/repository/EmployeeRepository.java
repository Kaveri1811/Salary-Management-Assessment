
package com.acme.salary_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.salary_management.entity.Employee;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByEmail(String email);
}
