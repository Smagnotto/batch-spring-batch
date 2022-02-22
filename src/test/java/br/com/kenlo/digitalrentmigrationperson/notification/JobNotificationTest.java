package br.com.kenlo.digitalrentmigrationperson.notification;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import br.com.kenlo.digitalrentmigrationperson.dto.StatusMigrationEnum;
import br.com.kenlo.digitalrentmigrationperson.notifications.JobNotification;
import br.com.kenlo.digitalrentmigrationperson.util.ProgressMigration;

public class JobNotificationTest {
    
    @Mock
    private ProgressMigration progressMigration;

    @InjectMocks
    private JobNotification notification;

    private JobExecution jobExecution;

    private Map<String, Object> params = new HashMap<>();


    @Before
    public void setUp() {
        params.put("id_imobiliaria", "1");
        jobExecution = mock(JobExecution.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldUpdateMigrationStatusBeforeJobStarted() {
        JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("id_imobiliaria", 1L)
                    .toJobParameters();

        when(jobExecution.getJobParameters()).thenReturn(jobParameters);
        doNothing().when(progressMigration).updateProgressMigration(eq(StatusMigrationEnum.STARTED), eq(1L));

        notification.beforeJob(jobExecution);

        verify(progressMigration).updateProgressMigration(eq(StatusMigrationEnum.STARTED), eq(1L));
        verifyNoMoreInteractions(progressMigration);

    }

    @Test
    public void shouldNotifyWhenJobFinishedSuccessfully() {
        JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("id_imobiliaria", 1L)
                    .toJobParameters();

        when(jobExecution.getJobParameters()).thenReturn(jobParameters);
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        doNothing().when(progressMigration).updateProgressMigration(eq(StatusMigrationEnum.FINISHED), eq(1L));

        notification.afterJob(jobExecution);

        verify(progressMigration).updateProgressMigration(eq(StatusMigrationEnum.FINISHED), eq(1L));
        verifyNoMoreInteractions(progressMigration);
    }

    @Test
    public void shouldNotifyWhenJobFinishedWithError() {
        JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("id_imobiliaria", 1L)
                    .toJobParameters();

        when(jobExecution.getJobParameters()).thenReturn(jobParameters);
        when(jobExecution.getStatus()).thenReturn(BatchStatus.FAILED);
        doNothing().when(progressMigration).updateProgressMigration(eq(StatusMigrationEnum.ERROR), eq(1L));

        notification.afterJob(jobExecution);

        verify(progressMigration).updateProgressMigration(eq(StatusMigrationEnum.ERROR), eq(1L));
        verifyNoMoreInteractions(progressMigration);
    }
}
