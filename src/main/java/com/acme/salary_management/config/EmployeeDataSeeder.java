
package com.acme.salary_management.config;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.acme.salary_management.entity.Department;
import com.acme.salary_management.entity.Employee;
import com.acme.salary_management.repository.DepartmentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component
@ConditionalOnProperty(
    name = "app.seed.enabled",
    havingValue = "true"
)
public class EmployeeDataSeeder implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    private final DepartmentRepository departmentRepository;

    public EmployeeDataSeeder(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    private static final String[] FIRST_NAMES = {
        "Rahul", "Priya", "Amit", "Sneha", "Arjun",
        "John", "Oliver", "Anna", "Kaveri", "David",
        "Neha", "Rohan", "Emily", "Daniel", "Sara"
    };

    private static final String[] LAST_NAMES = {
        "Sharma", "Patil", "Verma", "Kulkarni", "Reddy",
        "Smith", "Brown", "Miller", "Madiwal", "Wilson",
        "Gupta", "Mehta", "Taylor", "Martin", "Thomas"
    };

    private static final String[] COUNTRIES = {
        "India", "USA", "UK", "Germany"
    };

    private static final String[] CURRENCIES = {
        "INR", "USD", "GBP", "EUR"
    };

    private static final String[] JOB_TITLES = {
        "Software Developer",
        "Java Backend Developer",
        "HR Executive",
        "Financial Analyst",
        "Project Manager",
        "Business Analyst",
        "Marketing Executive",
        "Operations Manager",
        "Team Lead",
        "Software Engineer"
    };

    @Override
    @Transactional
    public void run(String... args) {

        List<Department> departments = departmentRepository.findAll();

        if (departments.isEmpty()) {
            throw new IllegalStateException(
                "Departments must exist before seeding employees."
            );
        }

        int totalEmployees = 100_000;
        int batchSize = 1_000;

        ThreadLocalRandom random = ThreadLocalRandom.current();

        System.out.println("Starting employee data generation...");

        for (int i = 1; i <= totalEmployees; i++) {

            String firstName =
                FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];

            String lastName =
                LAST_NAMES[random.nextInt(LAST_NAMES.length)];

            int countryIndex = random.nextInt(COUNTRIES.length);

            Employee employee = new Employee();

            employee.setEmployeeCode(
                String.format("ACME%06d", i)
            );

            employee.setFirstName(firstName);
            employee.setLastName(lastName);

            employee.setEmail(
                "employee" + i + "@acme.com"
            );

            employee.setDepartment(
                departments.get(random.nextInt(departments.size()))
            );

            employee.setJobTitle(
                JOB_TITLES[random.nextInt(JOB_TITLES.length)]
            );

            employee.setCountry(COUNTRIES[countryIndex]);
            employee.setCurrency(CURRENCIES[countryIndex]);

            employee.setAnnualSalary(
                BigDecimal.valueOf(
                    random.nextInt(400_000, 2_000_001)
                )
            );

            employee.setEmploymentStatus(
                random.nextInt(100) < 90 ? "ACTIVE" : "INACTIVE"
            );

            entityManager.persist(employee);

            if (i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();

                System.out.println(
                    "Generated " + i + " employees"
                );
            }
        }

        entityManager.flush();
        entityManager.clear();

        System.out.println(
            "Successfully generated " + totalEmployees + " employees!"
        );
    }
}
