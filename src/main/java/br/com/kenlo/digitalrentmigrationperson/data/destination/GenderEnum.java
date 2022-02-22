package br.com.kenlo.digitalrentmigrationperson.data.destination;

public enum GenderEnum {
    MASCULINE("MASCULINE"),
    FEMININE("FEMININE"),
    OUTHER("OUTHER");

    private String descricao;

    GenderEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
