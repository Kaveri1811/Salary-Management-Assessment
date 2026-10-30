


package com.acme.salary_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.salary_management.entity.Department;

public interface DepartmentRepository
        extends JpaRepository<Department, Long> {

}

