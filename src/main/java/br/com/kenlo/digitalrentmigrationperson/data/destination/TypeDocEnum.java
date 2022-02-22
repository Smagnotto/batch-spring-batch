package br.com.kenlo.digitalrentmigrationperson.data.destination;

public enum TypeDocEnum {
    RG("RG"),
    CPF("CPF"),
    CNPJ("CNPJ"),
    CNH("CNH"),
    CRECI("CRECI");

    private String descricao;

    TypeDocEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
