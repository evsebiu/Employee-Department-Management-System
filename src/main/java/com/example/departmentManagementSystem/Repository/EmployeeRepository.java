package com.example.departmentManagementSystem.Repository;

import com.example.departmentManagementSystem.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);
    List<Employee> findByLastNameContainingIgnoreCase(String lastName);
    List<Employee> findByEmailContainingIgnoreCase(String email);
    List<Employee> findBySalary(double salary);
    List<Employee> findByJoiningDate(LocalDate joiningDate);

    @Query("SELECT e FROM Employee e WHERE e.salary > :minSalary")
    List<Employee> findEmployeesWithSalaryGreaterThan(@Param("minSalary") double salary);

    //verify email does not exist
    boolean existsByEmail(String email);
}
