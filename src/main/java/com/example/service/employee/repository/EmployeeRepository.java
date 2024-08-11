package com.example.service.employee.repository;

import com.example.service.employee.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    boolean existsByCreci(String creci);

    Optional<Employee> findByIdAndActiveTrue(Long id);

    Page<Employee> findByActiveTrue(Pageable pageable);

    boolean existsByIdAndActiveTrue(Long id);


    Optional<Employee> findByCreciAndEmailAndTelephone(String creci, String email, String telephone);
}
