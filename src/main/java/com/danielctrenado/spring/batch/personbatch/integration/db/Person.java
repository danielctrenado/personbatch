package com.danielctrenado.spring.batch.personbatch.integration.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    private long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String nationality;
    private String sex;
    private String ssn;
    private String email;
    private String cellular;
    private String address1;
    private String address2;
    private String state;
    private String city;
    private String zip;
}
