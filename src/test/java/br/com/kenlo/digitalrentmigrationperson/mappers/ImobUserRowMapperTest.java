package br.com.kenlo.digitalrentmigrationperson.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.kenlo.digitalrentmigrationperson.data.source.ImobUser;

public class ImobUserRowMapperTest {
    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldMapperFieldSet() throws SQLException {
        when(resultSet.getInt(ImobUserRowMapper.FIELD_ID_USUARIO)).thenReturn(1);
        when(resultSet.getString(ImobUserRowMapper.FIELD_NOME_USUARIO)).thenReturn("User Teste");
        when(resultSet.getString(ImobUserRowMapper.FIELD_EMAIL_USUARIO)).thenReturn("teste@teste.com");
        when(resultSet.getString(ImobUserRowMapper.FIELD_CRECI_USUARIO)).thenReturn("3294819011019");
        when(resultSet.getString(ImobUserRowMapper.FIELD_CPF_USUARIO)).thenReturn("481.195.381-41");
        when(resultSet.getString(ImobUserRowMapper.FIELD_FOTO_USUARIO)).thenReturn("");
        when(resultSet.getDate(ImobUserRowMapper.FIELD_DATA_NASCIMENTO_USUARIO)).thenReturn(null);
        when(resultSet.getInt(ImobUserRowMapper.FIELD_ID_IMOBILIARIA_USUARIO)).thenReturn(1);
        when(resultSet.getString(ImobUserRowMapper.FIELD_CELULAR_USUARIO)).thenReturn("(11) 98475-9900");
        when(resultSet.getString(ImobUserRowMapper.FIELD_TELEFONE_USUARIO)).thenReturn("(11) 8475-9900");
        when(resultSet.getBoolean(ImobUserRowMapper.FIELD_ATIVO_USUARIO)).thenReturn(true);

        ImobUserRowMapper mapper = new ImobUserRowMapper();
        ImobUser user = mapper.mapRow(resultSet, 1);

        assertEquals(user.getUserId(), 1);
        assertEquals(user.getName(), "User Teste");
        assertEquals(user.getEmail(), "teste@teste.com");
        assertEquals(user.getCreci(), "3294819011019");
        assertEquals(user.getCpf(), "481.195.381-41");
        assertEquals(user.getPhoto(), "");
        assertNull(user.getBirthDate());
        assertEquals(user.getAgencyId(), 1);
        assertEquals(user.getCell(), "(11) 98475-9900");
        assertEquals(user.getPhone(), "(11) 8475-9900");
        assertTrue(user.getActivate());
    }

    @Test
    public void shoudReturnNullIfResultSetIsNull() throws SQLException {
        ImobUserRowMapper mapper = new ImobUserRowMapper();

        assertNull(mapper.mapRow(null, 1));
    }
}
