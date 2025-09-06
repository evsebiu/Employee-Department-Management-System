package com.example.departmentManagementSystem.Service;

import com.example.departmentManagementSystem.Model.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    List<Employee> getByFirstName(String firstName);
    List<Employee> getByLastName(String lastName);
    List<Employee> getByEmail(String email);
    Optional<Employee> getEmployeeById(Long id);
    List<Employee> getBySalary(Double salary);
    Employee addEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employeeDetails);
    void deleteEmployee(Long id);

    List<Employee> getEmployeeByDepartmentId(Long departmentId);
    boolean existsByEmail(String email);

}
