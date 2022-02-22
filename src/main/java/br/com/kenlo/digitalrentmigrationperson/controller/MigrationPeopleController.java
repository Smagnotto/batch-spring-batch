package br.com.kenlo.digitalrentmigrationperson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.kenlo.digitalrentmigrationperson.data.response.StatusResponse;
import br.com.kenlo.digitalrentmigrationperson.data.source.JobParams;
import br.com.kenlo.digitalrentmigrationperson.util.JobUtil;

@RestController
@RequestMapping("migration-persons")
public class MigrationPeopleController {

    @Autowired
    private JobUtil jobUtil;

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StatusResponse migrationPersons(@RequestBody JobParams jobParams) throws Exception  {
        return jobUtil.executeJob(jobParams);
    }
}
