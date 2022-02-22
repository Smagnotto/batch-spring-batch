package br.com.kenlo.digitalrentmigrationperson.processors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.kenlo.digitalrentmigrationperson.data.destination.Persons;
import br.com.kenlo.digitalrentmigrationperson.data.source.ImobUser;
import br.com.kenlo.digitalrentmigrationperson.dto.VerifyPersonDTO;
import br.com.kenlo.digitalrentmigrationperson.util.PersonUtil;

public class UserProcessor implements ItemProcessor<ImobUser, Persons> {

    public static List<ImobUser> ignoredUsers = new ArrayList<ImobUser>();

    @Autowired
    private PersonUtil personUtil;

    @Override
    public Persons process(ImobUser item) throws Exception {

        var verifyPersonDTO = VerifyPersonDTO.builder()
                .imobId(item.getUserId())
                .email(item.getEmail())
                .isBroker(true)
                .build();

        if (personUtil.personAlreadyExist(verifyPersonDTO)) {
            item.setEmail("");
        }

        final Persons person = new Persons(item);

        personUtil.setPerson(verifyPersonDTO);

        return person;
    }
}
