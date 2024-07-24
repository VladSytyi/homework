package com.seargn.homework.repository;

import com.seargn.homework.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpoyeeRepo extends JpaRepository<Employee, Long> { }
