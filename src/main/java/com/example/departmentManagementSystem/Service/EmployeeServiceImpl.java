package com.example.departmentManagementSystem.Service;


import com.example.departmentManagementSystem.Exception.DuplicateResourceException;
import com.example.departmentManagementSystem.Exception.ResourceNotFoundException;
import com.example.departmentManagementSystem.Model.Department;
import com.example.departmentManagementSystem.Model.Employee;
import com.example.departmentManagementSystem.Repository.DepartmentRepository;
import com.example.departmentManagementSystem.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private final DepartmentRepository departmentRepo;

    private EmployeeRepository employeeRepo;
    public EmployeeServiceImpl(EmployeeRepository employeeRepo, DepartmentRepository departmentRepo){
        this.employeeRepo=employeeRepo;
        this.departmentRepo=departmentRepo;
    }

    @Override
    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }

    @Override
    public List<Employee> getByFirstName(String firstName){
        return employeeRepo.findByFirstNameContainingIgnoreCase(firstName);
    }

    @Override
    public List<Employee> getByLastName(String lastName){
        return employeeRepo.findByLastNameContainingIgnoreCase(lastName);
    }

    @Override
    public List<Employee> getByEmail(String email){
        return employeeRepo.findByEmailContainingIgnoreCase(email);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id){
        return employeeRepo.findById(id);
    }

    @Override
    public List<Employee> getBySalary(Double salary){
        return employeeRepo.findBySalary(salary);
    }

    @Override
    public Employee addEmployee(Employee employee){

        if (employeeRepo.existsByEmail(employee.getEmail())){
            throw new DuplicateResourceException("Email already exists: " + employee.getEmail());
        }

        return employeeRepo.save(employee);
    }

    @Override
    public List<Employee> getEmployeeByDepartmentId(Long departmentId){
        return employeeRepo.findEmployeeByDepartmentId(departmentId);
    }

    @Override
    public void deleteEmployee(Long id){
        employeeRepo.deleteById(id);
    }
    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails){
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee is not found: " + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setSalary(employeeDetails.getSalary());
        employee.setJoiningDate(employeeDetails.getJoiningDate());
        employee.setDepartment(employeeDetails.getDepartment());

        return employeeRepo.save(employee);
    }

    @Override
    public boolean existsByEmail(String email){
        return employeeRepo.existsByEmail(email);
    }

}
