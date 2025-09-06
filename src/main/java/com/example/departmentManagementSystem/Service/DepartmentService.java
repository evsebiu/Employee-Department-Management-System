package com.example.departmentManagementSystem.Service;


import com.example.departmentManagementSystem.Model.Department;
import com.example.departmentManagementSystem.Repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private DepartmentRepository departmentRepo;
    public DepartmentService(DepartmentRepository departmentRepo){
        this.departmentRepo=departmentRepo;
    }

    public List<Department> getAllDepartments(){
        return departmentRepo.findAll();
    }
    public Department addDepartment(Department department){
        try{
            Optional<Department> existing = departmentRepo.findByName(department.getName());
            if (existing.isPresent()){
                throw new IllegalArgumentException("Department already exists: " + department.getName());
            }

            return departmentRepo.save(department);

        } catch (IllegalArgumentException ex){
            System.out.println("Error " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            System.out.println("Unexpected error: " + ex.getMessage());
            throw ex;
        }
    }

    public void deleteDepartment(Long id){
        departmentRepo.deleteById(id);
    }

    public Optional<Department> getDepartmentById(Long id){
        return departmentRepo.findById(id);
    }
}
