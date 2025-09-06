package com.example.departmentManagementSystem.Service;


import com.example.departmentManagementSystem.Exception.DuplicateResourceException;
import com.example.departmentManagementSystem.Exception.ResourceInUseException;
import com.example.departmentManagementSystem.Exception.ResourceNotFoundException;
import com.example.departmentManagementSystem.Model.Department;
import com.example.departmentManagementSystem.Repository.DepartmentRepository;
import com.example.departmentManagementSystem.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {


    @Autowired
    EmployeeRepository employeeRepo;

    private final DepartmentRepository departmentRepo;


    public DepartmentServiceImpl(DepartmentRepository departmentRepo, EmployeeRepository employeeRepo){
        this.departmentRepo=departmentRepo;
        this.employeeRepo=employeeRepo;
    }

    @Override
    public List<Department> getAllDepartments(){
        return departmentRepo.findAll();
    }

    @Override
    public Optional<Department> findByName(String name){
        return departmentRepo.findByName(name);
    }


    @Override
    public Department addDepartment(Department department){
            Optional<Department> existing = departmentRepo.findByName(department.getName());
            if (existing.isPresent()){
                throw new DuplicateResourceException("Deparment already exists");
            }else{
                System.out.println("Department succesful created.");
            }
            return departmentRepo.save(department);
    }

    @Override
    public void deleteDepartment(Long id){
        if (employeeRepo.existsByDepartmentId(id)){
            throw new ResourceInUseException("Cannot delete department with ID: " + id + " because it has employees assigned");
        } else {
            System.out.println("Department deleted");
            departmentRepo.deleteById(id);
        }
    }

    @Override
    public Optional<Department> getDepartmentById(Long id){
        return departmentRepo.findById(id);
    }

    @Override
    public Department updateDepartment(Long id, Department departmentDetails){
        Department department = departmentRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Department id not found " + id));
        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());

        return departmentRepo.save(department);
    }

}
