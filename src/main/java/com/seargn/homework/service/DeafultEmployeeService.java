package com.seargn.homework.service;

import com.seargn.homework.exception.NotFound;
import com.seargn.homework.exception.ValidationException;
import com.seargn.homework.model.Employee;
import com.seargn.homework.repository.EmpoyeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeafultEmployeeService implements EmployeeService {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(DeafultEmployeeService.class);

    private final EmpoyeeRepo repo;

    @Autowired
    public DeafultEmployeeService(EmpoyeeRepo repo) {
        this.repo = repo;
    }

    @Override
    public Employee findById(Long id) throws NotFound {
        return repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee not found with id: {}", id);
                    return new NotFound("Employee not found");
                });

    }

    @Override
    @Transactional
    public Employee save(Employee employee) throws ValidationException {

        validateEmployee(employee);

        return repo.save(employee);
    }

    @Override
    public void validateEmployee(Employee employee) throws ValidationException {
        validateEmail(employee.getEmail());
        validatePhone(employee.getPhone());
        validateName(employee.getName());
        validateJobTitle(employee.getJobTitle());
    }


    private void validateEmail(String email) throws ValidationException {

        if (email == null || email.isBlank()) {
            throw new ValidationException("Email cannot be empty");
        }
        if (!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            throw new ValidationException("Email is not valid");
        }
    }

    private void validatePhone(String phone) throws ValidationException {

        if (phone == null || phone.isBlank()) {
            throw new ValidationException("Phone cannot be empty");
        }

        if (!phone.matches("^[0-9]{10}$")) {
            throw new ValidationException("Phone is not valid");
        }
    }

    private void validateName(String name) throws ValidationException {

        if (name == null || name.isBlank()) {
            throw new ValidationException("Name cannot be empty");
        }
    }

    private void validateJobTitle(String jobTitle) throws ValidationException {

        if (jobTitle == null || jobTitle.isBlank()) {
            throw new ValidationException("Job title cannot be empty");
        }
    }
}
