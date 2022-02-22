package br.com.kenlo.digitalrentmigrationperson.data.source;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobParams {
    @JsonProperty("imobId")
    public Long idImobiliaria;

    @JsonProperty("service")
    public String service;
}
