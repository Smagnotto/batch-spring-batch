package br.com.kenlo.digitalrentmigrationperson.processors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.kenlo.digitalrentmigrationperson.data.destination.Persons;
import br.com.kenlo.digitalrentmigrationperson.data.source.ImobClient;
import br.com.kenlo.digitalrentmigrationperson.dto.VerifyPersonDTO;
import br.com.kenlo.digitalrentmigrationperson.util.PersonUtil;

public class ClientProcessor implements ItemProcessor<ImobClient, Persons> {

    @Autowired
    private PersonUtil personUtil;

    @Override
    public Persons process(ImobClient item) throws Exception {

        var verifyPersonDTO = VerifyPersonDTO.builder()
                .imobId(item.getClientId())
                .email(item.getEmail())
                .isBroker(false)
                .build();

        if (personUtil.personAlreadyExist(verifyPersonDTO)) {
            item.setEmail("");
        }

        final Persons person = new Persons(item);

        personUtil.setPerson(verifyPersonDTO);

        return person;
    }
}
