package com.example.service.employee.model;

import com.example.service.employee.domain.Employee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DataEmployee(
        Long id,
        @NotNull
        @Pattern(regexp ="[a-z]{2,}")
        String name,
        @NotNull
        @Pattern(regexp ="[a-z]{2,}")
        String surname,
        @NotNull
        @Pattern(regexp ="[0-9]{6}")
        String creci,
        @NotNull
        @Email
        String email,
        @NotNull
        @Pattern(regexp ="(\\+[0-9]{1,3}[0-9]{1,3}[0-9]{9})")
        String telephone,
        @NotNull
        @Valid
        DataAddressDTO address,
        @NotNull
        TypeBroker broker

) {
        public DataEmployee(Employee employee) {
                this(employee.getId(), employee.getName(), employee.getSurname(), employee.getCreci(), employee.getEmail(),
                        employee.getTelephone()
                        , new DataAddressDTO(employee.getAddress())
                        ,employee.getBroker());
        }
}
