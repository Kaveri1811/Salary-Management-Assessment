
package com.acme.salary_management.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.salary_management.dto.EmployeeRequest;
import com.acme.salary_management.dto.SalaryRevisionRequest;
import com.acme.salary_management.entity.Department;
import com.acme.salary_management.entity.Employee;
import com.acme.salary_management.entity.SalaryHistory;
import com.acme.salary_management.repository.DepartmentRepository;
import com.acme.salary_management.repository.EmployeeRepository;
import com.acme.salary_management.repository.SalaryHistoryRepository;
import com.acme.salary_management.specification.EmployeeSpecification;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final SalaryHistoryRepository salaryHistoryRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository, SalaryHistoryRepository salaryHistoryRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.salaryHistoryRepository= salaryHistoryRepository;
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

    public Page<Employee> getAllEmployees(
        String search,
        String country,
        Long departmentId,
        String status,
        Pageable pageable) {

    return employeeRepository.findAll(
            EmployeeSpecification.withFilters(
                    search,
                    country,
                    departmentId,
                    status
            ),
            pageable
    );
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

@Transactional
public SalaryHistory reviseSalary(Long employeeId,
                                  SalaryRevisionRequest request) {

    Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() ->
                    new RuntimeException("Employee not found"));

    BigDecimal previousSalary = employee.getAnnualSalary();

    if (previousSalary.compareTo(request.getNewSalary()) == 0) {
        throw new RuntimeException("New salary is same as current salary");
    }

    SalaryHistory history = new SalaryHistory();

    history.setEmployee(employee);
    history.setPreviousSalary(previousSalary);
    history.setNewSalary(request.getNewSalary());
    history.setEffectiveDate(request.getEffectiveDate());
    history.setReason(request.getReason());

    // Update employee's current salary
    employee.setAnnualSalary(request.getNewSalary());

    employeeRepository.save(employee);

    return salaryHistoryRepository.save(history);
}

public List<SalaryHistory> getSalaryHistory(Long employeeId) {

    if (!employeeRepository.existsById(employeeId)) {
        throw new RuntimeException("Employee not found");
    }

    return salaryHistoryRepository
            .findByEmployeeIdOrderByEffectiveDateDesc(employeeId);
}
}
