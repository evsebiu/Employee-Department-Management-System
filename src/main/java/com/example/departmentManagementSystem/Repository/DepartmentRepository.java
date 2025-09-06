package com.example.departmentManagementSystem.Repository;

import com.example.departmentManagementSystem.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);

    //check if department already exists
    boolean existsByName(String name);
}
