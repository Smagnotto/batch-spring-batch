package br.com.kenlo.digitalrentmigrationperson.data.destination;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Agency {
    @Field(name = "id", targetType = FieldType.INT32)
    private Integer id;
    @Field(targetType = FieldType.STRING)
    private String name;
}
