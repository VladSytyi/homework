package com.seargn.homework.controller;


import com.seargn.homework.exception.NotFound;
import com.seargn.homework.exception.ValidationException;
import com.seargn.homework.model.Employee;
import com.seargn.homework.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DefaultController {

    private final EmployeeService employeeService;

    public DefaultController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(employeeService.findById(id));
        } catch (NotFound e) {
            // response with 413 status code
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(employeeService.save(employee));
        } catch (ValidationException e) {
            // response with 400 status code
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}
