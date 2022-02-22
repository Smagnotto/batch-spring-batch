package br.com.kenlo.digitalrentmigrationperson.tasklet;

import org.springframework.batch.core.StepExecution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import br.com.kenlo.digitalrentmigrationperson.data.destination.Persons;
import br.com.kenlo.digitalrentmigrationperson.util.PersonUtil;

public class GetAllPersonsTaskletTest {

    @Mock
    private PersonUtil personUtil;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private GetAllPersonsTasklet tasklet = new GetAllPersonsTasklet();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldClearAllPersonsFromMemory() throws Exception {

        StepContribution stepContribution = mock(StepContribution.class);
        StepExecution stepExecution = mock(StepExecution.class);
        StepContext stepContext = mock(StepContext.class);
        ChunkContext chunkContext = mock(ChunkContext.class);
        
        Map<String, Object> params = new HashMap<>();
        params.put("id_imobiliaria", "1");

        when(stepContext.getStepExecution()).thenReturn(stepExecution);
        when(chunkContext.getStepContext()).thenReturn(stepContext);
        when(stepContext.getJobParameters()).thenReturn(params);

        List<Persons> allPersons = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("agency.id").is(1L));

        when(mongoTemplate.find(eq(query), eq(Persons.class))).thenReturn(allPersons);

        RepeatStatus status = tasklet.execute(stepContribution, chunkContext);
        assertEquals(status, RepeatStatus.FINISHED);
        verify(mongoTemplate, times(1)).find(eq(query), eq(Persons.class));
        verifyNoMoreInteractions(mongoTemplate);
    }
}
