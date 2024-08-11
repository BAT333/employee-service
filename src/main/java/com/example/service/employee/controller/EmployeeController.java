package com.example.service.employee.controller;

import com.example.service.employee.model.DataEmployee;
import com.example.service.employee.model.DataEmployeeDTO;
import com.example.service.employee.model.DataEmployeeUpdateDTO;
import com.example.service.employee.service.EmployeeService;
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
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DataEmployee> register(@RequestBody @Valid DataEmployeeDTO employee, UriComponentsBuilder builder){
        var user = this.service.register(employee);
        var uri = builder.path("/employee/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(user);
    }
    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable("id") Long id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Page<DataEmployee>> allEmployee(@PageableDefault(sort = {"id"})Pageable pageable){
        return ResponseEntity.ok(this.service.all(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<DataEmployee> employee(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.service.employee(id));
    }
    //Update
    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity update(@PathVariable("id") Long id,@RequestBody DataEmployeeUpdateDTO employee){
        return ResponseEntity.ok(this.service.update(id,employee));
    }




}
