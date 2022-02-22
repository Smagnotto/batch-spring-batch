package br.com.kenlo.digitalrentmigrationperson.data.source;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SQSMessage {
    public JobParams data;

    public Boolean isValid() {
        return data != null && data.getIdImobiliaria() != null && StringUtils.isNotBlank(data.getService());
    }
}
