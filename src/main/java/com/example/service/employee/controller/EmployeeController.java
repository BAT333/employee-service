package com.example.service.employee.controller;

import com.example.service.employee.model.DataEmployee;
import com.example.service.employee.model.DataEmployeeDTO;
import com.example.service.employee.model.DataEmployeeUpdateDTO;
import com.example.service.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/employee")
@SecurityRequirement(name = "bearer-key")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping("/public/register")
    @Transactional
    @Operation(summary ="Register the employee", description = "register employee if everything is ok, return the employee's registered information")
    public ResponseEntity<DataEmployee> register(@RequestBody @Valid DataEmployeeDTO employee, UriComponentsBuilder builder){
        var user = this.service.register(employee);
        var uri = builder.path("/employee/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(user);
    }
    @DeleteMapping("/delete/{id}")
    @Transactional
    @Operation(summary ="Delete an employee", description = "Take a specific employee with his ID and delete it")
    public ResponseEntity delete(@PathVariable("id") Long id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    @Operation(summary ="Get all employees", description = "Returns a list of all employees")
    public ResponseEntity<Page<DataEmployee>> allEmployee(@PageableDefault(sort = {"id"})Pageable pageable){
        return ResponseEntity.ok(this.service.all(pageable));
    }
    @GetMapping("/{id}")
    @Operation(summary ="search for an employee", description = "search an employee by his id")
    public ResponseEntity<DataEmployee> employee(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.service.employee(id));
    }

    //Update
    @PutMapping("/update/{id}")
    @Transactional
    @Operation(summary ="Update employee information", description = "Search for an employee by ID and update the required information")
    public ResponseEntity update(@PathVariable("id") Long id,@RequestBody @Valid DataEmployeeUpdateDTO employee){
        return ResponseEntity.ok(this.service.update(id,employee));
    }




}
