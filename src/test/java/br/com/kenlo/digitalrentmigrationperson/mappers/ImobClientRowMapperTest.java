package br.com.kenlo.digitalrentmigrationperson.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.kenlo.digitalrentmigrationperson.data.source.ImobClient;

public class ImobClientRowMapperTest {
    
    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldMapperResultSet() throws SQLException {
        when(resultSet.getInt(ImobClientRowMapper.FIELD_ID_CLIENTE)).thenReturn(1);
        when(resultSet.getString(ImobClientRowMapper.FIELD_NOME_CLIENTE)).thenReturn("Cliente Teste");
        when(resultSet.getString(ImobClientRowMapper.FIELD_EMAIL_CLIENTE)).thenReturn("teste@teste.com");
        when(resultSet.getString(ImobClientRowMapper.FIELD_RG_CLIENTE)).thenReturn("32.948.190-1");
        when(resultSet.getString(ImobClientRowMapper.FIELD_CNPJ_CLIENTE)).thenReturn("58.091.019/0001-12");
        when(resultSet.getString(ImobClientRowMapper.FIELD_CPF_CLIENTE)).thenReturn("481.195.381-41");
        when(resultSet.getString(ImobClientRowMapper.FIELD_REFERENCIA_CLIENTE)).thenReturn("A9184091");
        when(resultSet.getString(ImobClientRowMapper.FIELD_NACIONALIDADE_CLIENTE)).thenReturn("Brasileiro");
        when(resultSet.getInt(ImobClientRowMapper.FIELD_ID_IMOBILIARIA_CLIENTE)).thenReturn(1);
        when(resultSet.getString(ImobClientRowMapper.FIELD_CELULAR_CLIENTE)).thenReturn("(11) 98475-9900");
        when(resultSet.getString(ImobClientRowMapper.FIELD_TELEFONE_CLIENTE)).thenReturn("(11) 8475-9900");

        ImobClientRowMapper mapper = new ImobClientRowMapper();
        ImobClient client = mapper.mapRow(resultSet, 1);

        assertEquals(client.getClientId(), 1);
        assertEquals(client.getName(), "Cliente Teste");
        assertEquals(client.getEmail(), "teste@teste.com");
        assertEquals(client.getRg(), "32.948.190-1");
        assertEquals(client.getCnpj(), "58.091.019/0001-12");
        assertEquals(client.getCpf(), "481.195.381-41");
        assertEquals(client.getRefImob(), "A9184091");
        assertEquals(client.getNationality(), "Brasileiro");
        assertEquals(client.getAgencyId(), 1);
        assertEquals(client.getCell(), "(11) 98475-9900");
        assertEquals(client.getPhone(), "(11) 8475-9900");
    }

    @Test
    public void shoudReturnNullIfResultSetIsNull() throws SQLException {
        ImobClientRowMapper mapper = new ImobClientRowMapper();

        assertNull(mapper.mapRow(null, 1));
    }
}
