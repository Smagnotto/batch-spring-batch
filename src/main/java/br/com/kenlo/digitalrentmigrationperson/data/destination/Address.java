package br.com.kenlo.digitalrentmigrationperson.data.destination;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    
    private ObjectId _id;
    private Boolean main;
    private String street;
    private Integer number;
    private String complement;
    private String district;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
