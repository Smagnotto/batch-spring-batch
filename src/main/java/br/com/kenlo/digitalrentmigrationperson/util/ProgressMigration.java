package br.com.kenlo.digitalrentmigrationperson.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.kenlo.digitalrentmigrationperson.dto.ProgressAgency;
import br.com.kenlo.digitalrentmigrationperson.dto.StatusMigrationEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProgressMigration {

    @Value("${apis.urls.agency}")
    private String url;

    public void updateProgressMigration(StatusMigrationEnum status, Long idImobiliaria) {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

        ProgressAgency progress = ProgressAgency.builder()
                .idImobiliaria(idImobiliaria)
                .status(status)
                .build();

        HttpEntity<ProgressAgency> request = new HttpEntity<>(progress);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Progress updated with success");
        } else {
            log.error("Error on update progress");
        }
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 30000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }
}
