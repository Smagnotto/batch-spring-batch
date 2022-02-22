package br.com.kenlo.digitalrentmigrationperson.data.source;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImobUser {
    private Integer userId;
    private String email;
    private String phone;
    private String cell;
    private String photo;
    private String name;
    private String cpf;
    private String creci;
    private Date birthDate;
    private Boolean activate;
    private Integer agencyId;
}   
