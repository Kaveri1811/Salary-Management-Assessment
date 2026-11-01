package com.acme.salary_management.specification;

import org.springframework.data.jpa.domain.Specification;

import com.acme.salary_management.entity.Employee;

import jakarta.persistence.criteria.JoinType;

public class EmployeeSpecification {

    public static Specification<Employee> withFilters(
            String search,
            String country,
            Long departmentId,
            String status) {

        return (root, query, cb) -> {

            var predicates = cb.conjunction();

            // Search by employee code, first name, last name or email
            if (search != null && !search.isBlank()) {

                String pattern = "%" + search.trim().toLowerCase() + "%";

                predicates = cb.and(predicates,
                        cb.or(
                                cb.like(cb.lower(root.get("employeeCode")), pattern),
                                cb.like(cb.lower(root.get("firstName")), pattern),
                                cb.like(cb.lower(root.get("lastName")), pattern),
                                cb.like(cb.lower(root.get("email")), pattern)
                        )
                );
            }

            // Filter by country
            if (country != null && !country.isBlank()) {
                predicates = cb.and(predicates,
                        cb.equal(
                                cb.lower(root.get("country")),
                                country.trim().toLowerCase()
                        )
                );
            }

            // Filter by department ID
            if (departmentId != null) {
                var department = root.join("department", JoinType.INNER);

                predicates = cb.and(predicates,
                        cb.equal(department.get("id"), departmentId)
                );
            }

            // Filter by employment status
            if (status != null && !status.isBlank()) {
                predicates = cb.and(predicates,
                        cb.equal(
                                cb.upper(root.get("employmentStatus")),
                                status.trim().toUpperCase()
                        )
                );
            }

            return predicates;
        };
    }
}