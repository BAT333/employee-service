package com.example.service.employee.model;

import jakarta.validation.constraints.Pattern;

public record DataUpdateAddressDTO(

        String street,

        String state,

        String neighborhood,

        @Pattern(regexp ="[0-9]{1,3}")
        String number,
        @Pattern(regexp ="[0-9]{8}")
        String cep,
        String complemento
) {
}
