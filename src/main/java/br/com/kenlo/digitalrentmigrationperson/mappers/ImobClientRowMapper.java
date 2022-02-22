package br.com.kenlo.digitalrentmigrationperson.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.kenlo.digitalrentmigrationperson.data.source.ImobClient;

public class ImobClientRowMapper implements RowMapper<ImobClient> {

    public static final String FIELD_ID_CLIENTE = "id_cliente";
    public static final String FIELD_NOME_CLIENTE = "tx_nome";
    public static final String FIELD_EMAIL_CLIENTE = "tx_email";
    public static final String FIELD_RG_CLIENTE = "tx_rg";
    public static final String FIELD_CNPJ_CLIENTE = "tx_cnpj";
    public static final String FIELD_CPF_CLIENTE = "tx_cpf";
    public static final String FIELD_REFERENCIA_CLIENTE = "tx_referencia";
    public static final String FIELD_NACIONALIDADE_CLIENTE = "tx_nacionalidade";
    public static final String FIELD_ID_IMOBILIARIA_CLIENTE = "id_imobiliaria";
    public static final String FIELD_CELULAR_CLIENTE = "tx_celular";
    public static final String FIELD_TELEFONE_CLIENTE = "tx_telefone_residencia";

	@Override
	public ImobClient mapRow(ResultSet rs, int rowNum) throws SQLException {

        if (rs == null)
            return null;

        final ImobClient client = ImobClient.builder()
                .clientId(rs.getInt(FIELD_ID_CLIENTE))
                .name(rs.getString(FIELD_NOME_CLIENTE))
                .email(rs.getString(FIELD_EMAIL_CLIENTE))
                .rg(rs.getString(FIELD_RG_CLIENTE))
                .cnpj(rs.getString(FIELD_CNPJ_CLIENTE))
                .cpf(rs.getString(FIELD_CPF_CLIENTE))
                .refImob(rs.getString(FIELD_REFERENCIA_CLIENTE))
                .nationality(rs.getString(FIELD_NACIONALIDADE_CLIENTE))
                .agencyId(rs.getInt(FIELD_ID_IMOBILIARIA_CLIENTE))
                .cell(rs.getString(FIELD_CELULAR_CLIENTE))
                .phone(rs.getString(FIELD_TELEFONE_CLIENTE))
                .build();

        return client;
    }
}
