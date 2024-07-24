package com.seargn.homework.controller;

import com.seargn.homework.exception.NotFound;
import com.seargn.homework.exception.ValidationException;
import com.seargn.homework.model.Employee;
import com.seargn.homework.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DefaultController defaultController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEmployeeReturnsEmployeeWhenFound() throws NotFound {
        Employee employee = new Employee(null, "name", "email", "jobTitle", "53253454");

        when(employeeService.findById(1L)).thenReturn(employee);

        ResponseEntity<Employee> response = defaultController.getEmployee(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    void getEmployeeReturnsPreconditionFailedWhenNotFound() throws NotFound {
        when(employeeService.findById(1L)).thenThrow(new NotFound("Employee not found"));

        ResponseEntity<Employee> response = defaultController.getEmployee(1L);

        assertEquals(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    void getEmployeeReturnsInternalServerErrorOnException() throws NotFound {
        when(employeeService.findById(1L)).thenThrow(new RuntimeException());

        ResponseEntity<Employee> response = defaultController.getEmployee(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void saveEmployeeReturnsEmployeeWhenValid() throws ValidationException {
        Employee employee = new Employee();
        when(employeeService.save(employee)).thenReturn(employee);

        ResponseEntity<Employee> response = defaultController.saveEmployee(employee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    void saveEmployeeReturnsBadRequestOnValidationException() throws ValidationException {
        Employee employee = new Employee();
        when(employeeService.save(employee)).thenThrow(new ValidationException("Email cannot be empty"));

        ResponseEntity<Employee> response = defaultController.saveEmployee(employee);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void saveEmployeeReturnsInternalServerErrorOnException() throws ValidationException {
        Employee employee = new Employee();
        when(employeeService.save(employee)).thenThrow(new RuntimeException());

        ResponseEntity<Employee> response = defaultController.saveEmployee(employee);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}