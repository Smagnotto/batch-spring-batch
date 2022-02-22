package br.com.kenlo.digitalrentmigrationperson.data.destination;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import br.com.kenlo.digitalrentmigrationperson.data.source.ImobClient;
import br.com.kenlo.digitalrentmigrationperson.data.source.ImobUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "persons")
public class Persons {

    public Persons(ImobUser user) {
        this.setName(user.getName());
        this.setImobId(user.getUserId());
        this.setIsBroker(true);
        this.setIsActive(user.getActivate());
        this.setIsLegal(false);
        this.setAgency(new Agency(user.getAgencyId(), ""));
        this.setPhoto(user.getPhoto());

        this.setRegister(RegisterEnum.COMPLETE);

        if (user.getBirthDate() != null)
            this.setBirthDate(user.getBirthDate());

        if (StringUtils.isNotBlank(user.getEmail()))
            this.setEmail(user.getEmail().trim());

        this.addDoc(user.getCpf(), TypeDocEnum.CPF);
        this.addDoc(user.getCreci(), TypeDocEnum.CRECI);

        this.addPhone(user.getCell(), PhoneEnum.CELL);
        this.addPhone(user.getPhone(), PhoneEnum.LANDLINE);

        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
    }

    public Persons(ImobClient client) {
        this.setName(client.getName());
        this.setRefimob(client.getRefImob());
        this.setImobId(client.getClientId());
        this.setIsBroker(false);
        this.setIsActive(true);
        this.setIsLegal(false);
        this.setAgency(new Agency(client.getAgencyId(), ""));
        this.setNationality(client.getNationality());

        this.setRegister(RegisterEnum.COMPLETE);

        this.addDoc(client.getCpf(), TypeDocEnum.CPF);
        this.addDoc(client.getRg(), TypeDocEnum.RG);
        this.addDoc(client.getCnpj(), TypeDocEnum.CNPJ);
        this.addPhone(client.getCell(), PhoneEnum.CELL);
        this.addPhone(client.getPhone(), PhoneEnum.LANDLINE);

        if (StringUtils.isNotBlank(client.getEmail()))
            this.setEmail(client.getEmail().trim());

        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
    }

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private ObjectId _id;

    @Field(targetType = FieldType.IMPLICIT)
    private Agency agency;

    @Field(targetType = FieldType.ARRAY)
    private Set<Address> address = new HashSet<>();

    @Field(targetType = FieldType.DATE_TIME)
    private Date birthDate;

    @Field(targetType = FieldType.ARRAY)
    private Set<Doc> docs = new HashSet<>();

    @Field(targetType = FieldType.STRING)
    private String email;

    @Enumerated(EnumType.STRING)
    @Field(targetType = FieldType.STRING)
    private GenderEnum gender;

    @Field(targetType = FieldType.BOOLEAN)
    private Boolean isBroker;

    @Field(targetType = FieldType.BOOLEAN)
    private Boolean isActive;

    @Field(targetType = FieldType.BOOLEAN)
    private Boolean isLegal;

    @Enumerated(EnumType.STRING)
    @Field(targetType = FieldType.STRING)
    private MaritalEnum marital;

    @Field(targetType = FieldType.STRING)
    private String name;

    @Field(targetType = FieldType.STRING)
    private String nationality;

    @Field(targetType = FieldType.ARRAY)
    private Set<Phone> phones = new HashSet<>();

    @Field(targetType = FieldType.STRING)
    private String photo;

    @Field(targetType = FieldType.STRING)
    private String refimob;

    @Field(targetType = FieldType.STRING)
    @Enumerated(EnumType.STRING)
    private RegisterEnum register;

    @Field(targetType = FieldType.INT32)
    private Integer imobId;

    @Field(targetType = FieldType.DATE_TIME)
    private Date createdAt;

    @Field(targetType = FieldType.DATE_TIME)
    private Date updatedAt;

    public void addPhone(String phone, PhoneEnum type) {
        String newPhone = StringUtils.defaultString(phone).replaceAll("[^0-9]", "");

        if (StringUtils.isNotBlank(newPhone))
            phones.add(new Phone(type, Long.parseLong(newPhone)));
    }

    public void addDoc(String doc, TypeDocEnum type) {
        String newDoc = StringUtils.defaultString(doc).replaceAll("[^0-9]", "");

        if (StringUtils.isNotBlank(newDoc))
            docs.add(new Doc(type, newDoc));
    }
}