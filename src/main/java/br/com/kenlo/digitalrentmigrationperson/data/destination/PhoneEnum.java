package br.com.kenlo.digitalrentmigrationperson.data.destination;

public enum PhoneEnum {
    LANDLINE("LANDLINE"),
    CELL("CELL");

    private String descricao;

    PhoneEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
