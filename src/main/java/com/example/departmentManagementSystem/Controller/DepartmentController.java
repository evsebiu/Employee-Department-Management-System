package com.example.departmentManagementSystem.Controller;


import com.example.departmentManagementSystem.Exception.DuplicateResourceException;
import com.example.departmentManagementSystem.Exception.ResourceInUseException;
import com.example.departmentManagementSystem.Exception.ResourceNotFoundException;
import com.example.departmentManagementSystem.Model.Department;
import com.example.departmentManagementSystem.Service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Optional<Department> department = departmentService.getDepartmentById(id);
        return department.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        try {
            Department updateDepartment = departmentService.updateDepartment(id, department);
            return ResponseEntity.ok(updateDepartment);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DuplicateResourceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable Long id){
        try{
            departmentService.deleteDepartment(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e){
            return ResponseEntity.noContent().build();
        } catch (ResourceInUseException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody @Valid Department department){
        try {
            Department saveDepartment = departmentService.addDepartment(department);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveDepartment);
        } catch (DuplicateResourceException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Optional<Department>> searchDepartments(@RequestParam(required = false) String name){
        Optional<Department> departments = departmentService.findByName(name);
        if (departments.isPresent()) {
            return ResponseEntity.ok(departments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
