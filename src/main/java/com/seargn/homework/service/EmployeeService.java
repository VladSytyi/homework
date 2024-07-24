package com.seargn.homework.service;

import com.seargn.homework.exception.NotFound;
import com.seargn.homework.exception.ValidationException;
import com.seargn.homework.model.Employee;

public interface EmployeeService {
    Employee findById(Long id) throws NotFound;

    Employee save(Employee employee) throws ValidationException;

    void validateEmployee(Employee employee) throws ValidationException;
}
