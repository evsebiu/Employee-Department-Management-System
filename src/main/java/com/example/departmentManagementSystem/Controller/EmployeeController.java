package com.example.departmentManagementSystem.Controller;



import com.example.departmentManagementSystem.Exception.DuplicateResourceException;
import com.example.departmentManagementSystem.Exception.ResourceInUseException;
import com.example.departmentManagementSystem.Exception.ResourceNotFoundException;
import com.example.departmentManagementSystem.Model.Employee;
import com.example.departmentManagementSystem.Service.EmployeeService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/firstName/{firstName}")
    public ResponseEntity<List<Employee>> getEmployeeByFirstName(@PathVariable String firstName){
      List<Employee> employees = employeeService.getByFirstName(firstName);

      if (employees.isEmpty()){
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(employees);
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<List<Employee>> getEmployeeByLastName(@PathVariable String lastName){
        List<Employee> employees = employeeService.getByLastName(lastName);

        if (employees.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Employee>> getEmployeeByEmail(@PathVariable String email){
        List<Employee> employees = employeeService.getByEmail(email);

        if (employees.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeeByDepartmentId(@PathVariable Long departmentId){
        List<Employee> employees = employeeService.getEmployeeByDepartmentId(departmentId);

        if (employees.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        try{
            Employee updateEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updateEmployee);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (DuplicateResourceException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/salary/{salary}")
    public ResponseEntity<List<Employee>> getEmployeesBySalary(@PathVariable Double salary){
        List<Employee> employees = employeeService.getBySalary(salary);
        if (employees.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id){
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (ResourceInUseException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee){
        try {
            Employee createEmployee = employeeService.addEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createEmployee);
        } catch (DuplicateResourceException e){
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
