package com.example.service.employee.service;

import com.example.service.employee.config.exceptions.EmployeeException;
import com.example.service.employee.domain.Employee;
import com.example.service.employee.model.DataEmployee;
import com.example.service.employee.model.DataEmployeeDTO;
import com.example.service.employee.model.DataEmployeeUpdateDTO;
import com.example.service.employee.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;
    public DataEmployee register(DataEmployeeDTO employee) {
        log.info("Registering new employee: {}", employee.creci());
        if(repository.findByCreciAndEmailAndTelephone(employee.creci(),employee.email(),employee.telephone()).isPresent()){
           log.error("erro ao registrar funcionario: {}", employee.creci());
            throw new EmployeeException();
        }
        var user = this.repository.save(new Employee(employee));
        return new DataEmployee(user);
    }

    public void delete(Long id) {
        log.info("deleting employee: {}", id);
        if(!repository.existsByIdAndActiveTrue(id)){
            log.error("error deleting employee: {}", id);
            throw new EmployeeException();
        }
        this.repository.findByIdAndActiveTrue(id).ifPresent(Employee::delete);
    }

    public Page<DataEmployee> all(Pageable pageable) {
        log.info("listing all employees");
        var list = this.repository.findByActiveTrue(pageable);
        return list.map(DataEmployee::new);
    }

    public DataEmployee employee(Long id) {
        log.info("searching for specific employee : {}", id);
        if(!repository.existsById(id)){
            log.error("error searching for employee: {}", id);
            throw new EmployeeException();
        }
        return this.repository.findById(id).map(DataEmployee::new).get();
    }

    public DataEmployee update(Long id, DataEmployeeUpdateDTO employee) {
        log.info("make an employee information update: {}", id);
        if(!repository.existsByIdAndActiveTrue(id)||!repository.existsByCreci(employee.creci())){
            log.error("error when doing an employee update: {}", id);
            throw new EmployeeException();
        }
        var user = this.repository.findByIdAndActiveTrue(id);
        user.ifPresent(em -> em.update(employee));
        return user.map(DataEmployee::new).get();

    }
}
