package br.com.kenlo.digitalrentmigrationperson.dto;

public enum StatusMigrationEnum {
    STARTED("STARTED"),
    FINISHED("FINISHED"),
    ERROR("ERROR");

    private String descricao;

    StatusMigrationEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
