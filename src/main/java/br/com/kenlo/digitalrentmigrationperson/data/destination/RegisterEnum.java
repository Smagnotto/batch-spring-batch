package br.com.kenlo.digitalrentmigrationperson.data.destination;

public enum RegisterEnum {
    PARTIAL("PARTIAL"),
    COMPLETE("COMPLETE");

    private String descricao;

    RegisterEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static RegisterEnum fromValue(String value) {
        if (value != null) {
            for (RegisterEnum register : values()) {
                if (register.descricao.equals(value)) {
                    return register;
                }
            }
        }

        throw new IllegalArgumentException("Invalid Register Value: " + value);
    }
}
