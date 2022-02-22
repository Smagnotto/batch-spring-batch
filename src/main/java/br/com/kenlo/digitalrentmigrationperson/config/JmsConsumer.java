package br.com.kenlo.digitalrentmigrationperson.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import br.com.kenlo.digitalrentmigrationperson.data.source.SQSMessage;
import br.com.kenlo.digitalrentmigrationperson.util.JobUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JmsConsumer {

    private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();
    private static final String SQS_SERVICE = "MIGRATION_PERSONS";

    @Autowired
    private JobUtil jobUtil;

    @JmsListener(destination = "${aws.sqs.queue.name}")
    public void processMessage(String message) throws Exception {
        SQSMessage body = OBJECT_MAPPER.readValue(message, SQSMessage.class);

        if (!body.isValid()) {
            throw new IllegalArgumentException("Message received is invalid ::: message ->" + message);
        }

        if (body.getData().getService().equals(SQS_SERVICE)) {
            log.info("Running job for real estate: " + body.getData().getIdImobiliaria());
            jobUtil.executeJob(body.getData());
        } else
            log.warn("Service not found for this job!");
    }
}
