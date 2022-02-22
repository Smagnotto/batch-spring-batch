package br.com.kenlo.digitalrentmigrationperson.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.kenlo.digitalrentmigrationperson.data.source.ImobUser;


public class ImobUserRowMapper implements RowMapper<ImobUser> {

    public static final String FIELD_ID_USUARIO = "id_usuario";
    public static final String FIELD_NOME_USUARIO = "tx_nome_completo";
    public static final String FIELD_EMAIL_USUARIO = "tx_email";
    public static final String FIELD_CRECI_USUARIO = "tx_creci";
    public static final String FIELD_CPF_USUARIO = "tx_cpf";
    public static final String FIELD_ID_IMOBILIARIA_USUARIO = "id_imobiliaria";
    public static final String FIELD_CELULAR_USUARIO = "tx_celular";
    public static final String FIELD_TELEFONE_USUARIO = "tx_telefone";
    public static final String FIELD_FOTO_USUARIO = "tx_foto";
    public static final String FIELD_DATA_NASCIMENTO_USUARIO = "dt_nascimento";
    public static final String FIELD_ATIVO_USUARIO = "fl_ativo";

	@Override
	public ImobUser mapRow(ResultSet rs, int rowNum) throws SQLException {


        
        if (rs == null)
            return null;

        final ImobUser client = ImobUser.builder()
                .userId(rs.getInt(FIELD_ID_USUARIO))
                .email(rs.getString(FIELD_EMAIL_USUARIO))
                .phone(rs.getString(FIELD_TELEFONE_USUARIO))
                .cell(rs.getString(FIELD_CELULAR_USUARIO))
                .photo(rs.getString(FIELD_FOTO_USUARIO))
                .name(rs.getString(FIELD_NOME_USUARIO))
                .cpf(rs.getString(FIELD_CPF_USUARIO))
                .creci(rs.getString(FIELD_CRECI_USUARIO))
                .birthDate(rs.getDate(FIELD_DATA_NASCIMENTO_USUARIO))
                .activate(rs.getBoolean(FIELD_ATIVO_USUARIO))
                .agencyId(rs.getInt(FIELD_ID_IMOBILIARIA_USUARIO))
                .build();

        return client;
    }
}
