package com.example.departmentManagementSystem.Service;

import com.example.departmentManagementSystem.Model.Department;
import org.apache.catalina.security.DeployXmlPermission;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Optional<Department> findByName(String name);
    Department addDepartment(Department department);
    void deleteDepartment(Long id);
    Department updateDepartment(Long id, Department department);
    Optional<Department> getDepartmentById(Long id);
}
