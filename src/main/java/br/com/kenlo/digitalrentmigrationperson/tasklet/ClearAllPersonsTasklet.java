package br.com.kenlo.digitalrentmigrationperson.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.kenlo.digitalrentmigrationperson.util.PersonUtil;

public class ClearAllPersonsTasklet implements Tasklet {
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PersonUtil personUtil;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        personUtil.clearPersons();

        return RepeatStatus.FINISHED;
    }
}
