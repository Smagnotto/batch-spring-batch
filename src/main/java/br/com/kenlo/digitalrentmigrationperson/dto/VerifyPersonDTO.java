package br.com.kenlo.digitalrentmigrationperson.dto;

import org.apache.commons.lang3.StringUtils;

import br.com.kenlo.digitalrentmigrationperson.data.destination.Persons;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class VerifyPersonDTO {
    
    public VerifyPersonDTO(Persons person) {
        this.email = person.getEmail();
        this.imobId = person.getImobId();
        this.isBroker = person.getIsBroker();
    }

    private String email;
    private Integer imobId;
    private Boolean isBroker;

    public String getEmailValue(){
        return StringUtils.isBlank(email) ? "" : email.trim();
    }

    public Integer getImobIdValue() {
        return imobId == null ? -1 : imobId;
    }
}
