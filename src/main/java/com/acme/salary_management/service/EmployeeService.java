
package com.acme.salary_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.salary_management.dto.EmployeeRequest;
import com.acme.salary_management.entity.Department;
import com.acme.salary_management.entity.Employee;
import com.acme.salary_management.repository.DepartmentRepository;
import com.acme.salary_management.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public Employee createEmployee(EmployeeRequest request) {

        if (employeeRepository.existsByEmployeeCode(
                request.getEmployeeCode())) {
            throw new IllegalArgumentException(
                    "Employee code already exists");
        }

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already exists");
        }

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found"));

        Employee employee = new Employee();

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);
        employee.setJobTitle(request.getJobTitle());
        employee.setCountry(request.getCountry());
        employee.setCurrency(request.getCurrency());
        employee.setAnnualSalary(request.getAnnualSalary());
        employee.setEmploymentStatus(request.getEmploymentStatus());

        return employeeRepository.save(employee);
    }

    public Page<Employee> getAllEmployees(Pageable pageable) {
    return employeeRepository.findAll(pageable);
}

public Employee getEmployeeById(Long id) {
    return employeeRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Employee not found with ID: " + id));
}

    public Employee updateEmployee(Long id, EmployeeRequest request) {

    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Employee not found with ID: " + id));

    // Check duplicate employee code
    if (!employee.getEmployeeCode().equals(request.getEmployeeCode())
            && employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
        throw new RuntimeException("Employee code already exists");
    }

    // Check duplicate email
    if (!employee.getEmail().equals(request.getEmail())
            && employeeRepository.existsByEmail(request.getEmail())) {
        throw new RuntimeException("Email already exists");
    }

    Department department = departmentRepository.findById(request.getDepartmentId())
            .orElseThrow(() ->
                    new RuntimeException("Department not found"));

    employee.setEmployeeCode(request.getEmployeeCode());
    employee.setFirstName(request.getFirstName());
    employee.setLastName(request.getLastName());
    employee.setEmail(request.getEmail());
    employee.setDepartment(department);
    employee.setJobTitle(request.getJobTitle());
    employee.setCountry(request.getCountry());
    employee.setCurrency(request.getCurrency());
    employee.setAnnualSalary(request.getAnnualSalary());
    employee.setEmploymentStatus(request.getEmploymentStatus());

    return employeeRepository.save(employee);
}

public Employee updateEmployeeStatus(Long id, String status) {

    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Employee not found with ID: " + id));

    if (!status.equals("ACTIVE") && !status.equals("INACTIVE")) {
        throw new RuntimeException("Status must be ACTIVE or INACTIVE");
    }

    employee.setEmploymentStatus(status);

    return employeeRepository.save(employee);
}
}
