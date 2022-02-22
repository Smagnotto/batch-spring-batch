package br.com.kenlo.digitalrentmigrationperson.data.destination;

public enum MaritalEnum {
    MARRIED("MARRIED"),
    DIVORCED("DIVORCED"),
    SINGLE("SINGLE"),
    WIDOWER("WIDOWER"),
    STABLEUNION("STABLEUNION");

    private String descricao;

    MaritalEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
