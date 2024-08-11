package com.example.service.employee.domain;


import com.example.service.employee.model.DataAddressDTO;
import com.example.service.employee.model.DataUpdateAddressDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street",nullable = false)
    private String street;
    @Column(name = "state",nullable = false)
    private String state;
    @Column(name = "neighborhood",nullable = false)
    private String neighborhood;
    @Column(name = "number",nullable = false,length = 4)
    private String number;
    @Column(name = "cep",nullable = false,length = 8)
    private String cep;
    @Column(name = "complemento",length = 100)
    private String complemento;
    @Column(name ="actives",nullable = false)
    private Boolean active = true;

    public Address(DataAddressDTO address) {
        this.street = address.street();
        this.cep = address.cep();
        this.state = address.state();
        this.neighborhood = address.neighborhood();
        this.number = address.number();
        this.complemento = address.complemento();
    }

    public void update(DataUpdateAddressDTO address) {
        if(address.street()!=null){this.street = address.street();}
        if(address.cep()!=null){this.cep = address.cep();}
        if(address.complemento()!=null){this.complemento = address.complemento();}
        if(address.state()!=null){this.state = address.state();}
        if(address.number()!=null){this.number = address.number();}
        if(address.neighborhood()!=null){this.neighborhood = address.neighborhood();}
    }

    public void delete() {
        this.active = false;
    }
}
