package com.example.service.employee.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record DataEmployeeUpdateDTO(
        @Pattern(regexp ="[a-z]{2,}")
        String name,

        @Pattern(regexp ="[a-z]{2,}")
        String surname,

        @Pattern(regexp ="[0-9]{6}")
        String creci,

        @Email
        String email,

        @Pattern(regexp ="(\\+[0-9]{1,3}[0-9]{1,3}[0-9]{9})")
        String telephone,

        @Valid
        DataUpdateAddressDTO address,

        TypeBroker broker
) {
}
