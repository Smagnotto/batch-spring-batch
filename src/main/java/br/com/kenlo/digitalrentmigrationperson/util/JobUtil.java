package br.com.kenlo.digitalrentmigrationperson.util;

import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.kenlo.digitalrentmigrationperson.data.response.StatusResponse;
import br.com.kenlo.digitalrentmigrationperson.data.source.JobParams;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobUtil {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    public Job job;

    public StatusResponse executeJob(JobParams jobParams) throws Exception {
        try {
            Set<JobExecution> jobExecutions = jobExplorer.findRunningJobExecutions(job.getName());
            if (!jobExecutions.isEmpty()) {
                log.error("A job execution for this job is already running");
                return new StatusResponse(false, "A job execution for this job is already running");
            }

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("id_imobiliaria", jobParams.getIdImobiliaria())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            log.info("Job processed with success - Real Estate Id: " + jobParams.getIdImobiliaria());

            if (jobExecution.getStatus() == BatchStatus.COMPLETED)
                return new StatusResponse(true, "Job executed with success");
            else
                return new StatusResponse(false, "An error occurred while running batch");
        } catch (Exception e) {
            log.error("Exception", e);
            Throwable rootException = ExceptionUtils.getRootCause(e);
            String errMessage = rootException.getMessage();
            log.error("Root cause is instance of JobInstanceAlreadyCompleteException --> "
                    + (rootException instanceof JobInstanceAlreadyCompleteException));
            if (rootException instanceof JobInstanceAlreadyCompleteException) {
                log.error(errMessage, e);
                return new StatusResponse(false, "This job has been completed already!");
            } else if (rootException instanceof JobExecutionAlreadyRunningException) {
                log.error(errMessage, e);
                return new StatusResponse(false, "A job execution for this job is already running");
            } else {
                throw e;
            }
        }
    }
}
