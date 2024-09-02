package com.example.service.employee.service;

import com.example.service.employee.config.exceptions.EmployeeException;
import com.example.service.employee.domain.Employee;
import com.example.service.employee.model.DataEmployee;
import com.example.service.employee.model.DataEmployeeDTO;
import com.example.service.employee.model.DataEmployeeUpdateDTO;
import com.example.service.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;
    public DataEmployee register(DataEmployeeDTO employee) {
        if(repository.findByCreciAndEmailAndTelephone(employee.creci(),employee.email(),employee.telephone()).isPresent()){
            throw new EmployeeException();
        }
        var user = this.repository.save(new Employee(employee));
        return new DataEmployee(user);
    }

    public void delete(Long id) {
        if(!repository.existsByIdAndActiveTrue(id)){
            throw new EmployeeException();
        }
        this.repository.findByIdAndActiveTrue(id).ifPresent(Employee::delete);
    }

    public Page<DataEmployee> all(Pageable pageable) {
        var list = this.repository.findByActiveTrue(pageable);
        System.out.println(list);
        return list.map(DataEmployee::new);
    }

    public DataEmployee employee(Long id) {
        if(!repository.existsById(id)){
            throw new EmployeeException();
        }
        return this.repository.findById(id).map(DataEmployee::new).get();
    }

    public DataEmployee update(Long id, DataEmployeeUpdateDTO employee) {
        if(!repository.existsByIdAndActiveTrue(id)||!repository.existsByCreci(employee.creci())){
            throw new EmployeeException();
        }
        var user = this.repository.findByIdAndActiveTrue(id);
        user.ifPresent(em -> em.update(employee));
        return user.map(DataEmployee::new).get();

    }
}
