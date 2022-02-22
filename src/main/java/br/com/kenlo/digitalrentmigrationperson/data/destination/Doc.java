package br.com.kenlo.digitalrentmigrationperson.data.destination;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Doc {
    @Enumerated(EnumType.STRING)
    @Field(targetType = FieldType.STRING)
    private TypeDocEnum type;
    @Field(targetType = FieldType.STRING)
    private String value;
}
