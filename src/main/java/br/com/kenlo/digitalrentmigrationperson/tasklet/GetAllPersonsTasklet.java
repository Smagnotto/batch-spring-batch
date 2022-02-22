package br.com.kenlo.digitalrentmigrationperson.tasklet;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import br.com.kenlo.digitalrentmigrationperson.data.destination.Persons;
import br.com.kenlo.digitalrentmigrationperson.dto.VerifyPersonDTO;
import br.com.kenlo.digitalrentmigrationperson.util.PersonUtil;

public class GetAllPersonsTasklet implements Tasklet {

    @Autowired
    private PersonUtil personUtil;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Long idImobiliaria = Long.parseLong(chunkContext.getStepContext().getJobParameters().get("id_imobiliaria").toString());

        Query query = new Query();
        query.addCriteria(Criteria.where("agency.id").is(idImobiliaria));

        List<Persons> allPersons = mongoTemplate.find(query, Persons.class);

        List<VerifyPersonDTO> allPersonsToVerify = allPersons.stream().map(VerifyPersonDTO::new)
                .collect(Collectors.toList());

        personUtil.setPerson(allPersonsToVerify);

        return RepeatStatus.FINISHED;
    }
}
