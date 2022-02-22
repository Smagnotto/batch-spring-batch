package br.com.kenlo.digitalrentmigrationperson.tasklet;

import org.springframework.batch.core.StepExecution;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.MetaDataInstanceFactory;

import br.com.kenlo.digitalrentmigrationperson.util.PersonUtil;

public class ClearAllPersonsTaskletTest {
    
    @Mock
    private PersonUtil personUtil;

    @InjectMocks
    private ClearAllPersonsTasklet tasklet = new ClearAllPersonsTasklet();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldClearAllPersonsFromMemory() throws Exception {
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution();
        StepContribution stepContribution = new StepContribution(stepExecution);

        ChunkContext chunkContext = new ChunkContext(
            new StepContext(stepExecution)
        );

        RepeatStatus status = tasklet.execute(stepContribution, chunkContext);
        assertEquals(status, RepeatStatus.FINISHED);
    }
}
