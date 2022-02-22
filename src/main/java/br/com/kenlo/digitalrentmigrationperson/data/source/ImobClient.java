package br.com.kenlo.digitalrentmigrationperson.data.source;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImobClient {
    private int clientId;
    private String name;
    private String email;
    private String rg;
    private String cnpj;
    private String refImob;
    private String nationality;
    private Integer agencyId;
    private String cell;
    private String cpf;
    private String phone;
}
