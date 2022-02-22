package br.com.kenlo.digitalrentmigrationperson.notifications;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.kenlo.digitalrentmigrationperson.dto.StatusMigrationEnum;
import br.com.kenlo.digitalrentmigrationperson.util.ProgressMigration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobNotification extends JobExecutionListenerSupport {

    @Autowired
    private ProgressMigration progressMigration;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);

        Long idImobiliaria = jobExecution.getJobParameters().getLong("id_imobiliaria");
        progressMigration.updateProgressMigration(StatusMigrationEnum.STARTED, idImobiliaria);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        super.afterJob(jobExecution);

        Long idImobiliaria = jobExecution.getJobParameters().getLong("id_imobiliaria");
        StatusMigrationEnum status;

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("*** MIGRATION FINISHED ***");
            status = StatusMigrationEnum.FINISHED;
        } else {
            log.info("*** MIGRATION FAILED ***");
            log.error("::: Failure Exceptions :::", jobExecution.getFailureExceptions());
            log.error("::: Step Executions :::", jobExecution.getStepExecutions());
            status = StatusMigrationEnum.ERROR;
        }

        progressMigration.updateProgressMigration(status, idImobiliaria);
    }
}
