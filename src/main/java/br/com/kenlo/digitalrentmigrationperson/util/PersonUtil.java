package br.com.kenlo.digitalrentmigrationperson.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.kenlo.digitalrentmigrationperson.dto.VerifyPersonDTO;
import lombok.Getter;

@Component
public class PersonUtil {

    @Getter
    private List<VerifyPersonDTO> allPersons = new ArrayList<>();

    public void setPerson(VerifyPersonDTO person) {
        allPersons.add(person);
    }

    public void setPerson(Collection<? extends VerifyPersonDTO> list) {
        allPersons.addAll(list);
    }

    public Boolean personAlreadyExist(VerifyPersonDTO person) {
        Boolean foundEmail = false;
        Boolean foundImobId = false;

        if (StringUtils.isNotBlank(person.getEmail()))
            foundEmail = allPersons.parallelStream()
                    .anyMatch(p -> p.getEmailValue().equals(person.getEmail().trim()) 
                                && p.getIsBroker() == person.getIsBroker());

        if (person.getImobId() != null)
            foundImobId = allPersons.parallelStream()
                    .anyMatch(p -> p.getImobIdValue().equals(person.getImobId())
                                && p.getIsBroker() == person.getIsBroker());


        return foundEmail || foundImobId;

    }

    public void clearPersons() {
        allPersons.clear();
    }
}
