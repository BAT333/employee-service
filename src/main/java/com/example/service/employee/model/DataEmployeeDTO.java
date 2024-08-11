package com.example.service.employee.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DataEmployeeDTO(

        @NotNull
        @Pattern(regexp ="[A-Za-z]")
        String name,
        @NotNull
        @Pattern(regexp ="[A-Za-z]")
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
}
