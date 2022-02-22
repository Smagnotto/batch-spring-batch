package br.com.kenlo.digitalrentmigrationperson.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StatusResponse {
    
    public StatusResponse(boolean success) {
        this.success = success;
    }

    public boolean success;
    public String message;
}
