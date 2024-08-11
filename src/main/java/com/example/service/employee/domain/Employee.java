package com.example.service.employee.domain;

import com.example.service.employee.model.DataEmployeeDTO;
import com.example.service.employee.model.DataEmployeeUpdateDTO;
import com.example.service.employee.model.TypeBroker;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "surname",nullable = false)
    private String surname;
    @Column(name = "CRECI",nullable = false,unique = true)
    private String creci;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "Telephone",nullable = false,unique = true)
    private String telephone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address",nullable = false)
    private Address address;
    @Column(name = "broker_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeBroker broker;
    @Column(name ="actives",nullable = false)
    private Boolean active = true;

    public Employee(DataEmployeeDTO employee) {
        this.name = employee.name();
        this.surname = employee.surname();
        this.creci = employee.creci();
        this.email = employee.email();
        this.telephone = employee.telephone();
        this.address = new Address(employee.address());
        this.broker = employee.broker();
        this.active = true;
    }

    public void delete() {
        this.active = false;
        this.address.delete();
    }

    public Employee update(DataEmployeeUpdateDTO employee) {
        if(employee.address() != null){
            this.address.update(employee.address());
        }
        if(employee.telephone()!= null ){
            this.telephone = employee.telephone();
        }
        if(employee.name()!= null ){
            this.name = employee.name();
        }
        if(employee.creci()!= null ){
            this.creci = employee.creci();
        }
        if(employee.email()!= null ){
            this.email = employee.email();
        }
        if(employee.surname()!= null ){
            this.surname = employee.surname();
        }
        return this;
    }
}
