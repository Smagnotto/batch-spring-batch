package br.com.kenlo.digitalrentmigrationperson.processors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.kenlo.digitalrentmigrationperson.data.destination.Doc;
import br.com.kenlo.digitalrentmigrationperson.data.destination.Persons;
import br.com.kenlo.digitalrentmigrationperson.data.destination.Phone;
import br.com.kenlo.digitalrentmigrationperson.data.destination.PhoneEnum;
import br.com.kenlo.digitalrentmigrationperson.data.destination.TypeDocEnum;
import br.com.kenlo.digitalrentmigrationperson.data.source.ImobClient;
import br.com.kenlo.digitalrentmigrationperson.util.PersonUtil;

public class ClientProcessorTest {

    @Mock
    private PersonUtil personUtil;

    @InjectMocks
    private ClientProcessor processor = new ClientProcessor();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldProcessClientWithSuccess() throws Exception {
        when(this.personUtil.personAlreadyExist(any())).thenReturn(false);

        final ImobClient imobClient = ImobClient
                .builder()
                .agencyId(1)
                .cell("(11) 98476-7165")
                .clientId(1)
                .cnpj(null)
                .cpf("481.185.305-41")
                .email("teste@gmail.com")
                .name("Teste")
                .nationality("")
                .phone("(11) 94857-8471")
                .refImob("947819A")
                .rg("32.481.393-X")
                .build();

        Persons result = processor.process(imobClient);

        Set<Phone> phones = new HashSet<>();
        phones.add(new Phone(PhoneEnum.LANDLINE, 11948578471L));
        phones.add(new Phone(PhoneEnum.CELL, 11984767165L));

        Set<Doc> docs = new HashSet<>();
        docs.add(new Doc(TypeDocEnum.RG, "32481393"));
        docs.add(new Doc(TypeDocEnum.CPF, "48118530541"));

        assertEquals(result.getAgency().getId(), 1);
        assertEquals(result.getPhones(), phones);
        assertEquals(result.getDocs(), docs);
        assertEquals(result.getImobId(), 1);
        assertEquals(result.getEmail(), "teste@gmail.com");
        assertEquals(result.getName(), "Teste");
        assertEquals(result.getNationality(), "");
        assertEquals(result.getRefimob(), "947819A");

    }

    @Test
    public void shouldClearEmailWhenClientAlreadyExist() throws Exception {
        when(this.personUtil.personAlreadyExist(any())).thenReturn(true);

        final ImobClient imobClient = ImobClient
                .builder()
                .agencyId(1)
                .cell("(11) 98476-7165")
                .clientId(1)
                .cnpj(null)
                .cpf("481.185.305-41")
                .email("teste@gmail.com")
                .name("Teste")
                .nationality("")
                .phone("(11) 94857-8471")
                .refImob("947819A")
                .rg("32.481.393-X")
                .build();

        Persons result = processor.process(imobClient);

        Set<Phone> phones = new HashSet<>();
        phones.add(new Phone(PhoneEnum.LANDLINE, 11948578471L));
        phones.add(new Phone(PhoneEnum.CELL, 11984767165L));

        Set<Doc> docs = new HashSet<>();
        docs.add(new Doc(TypeDocEnum.RG, "32481393"));
        docs.add(new Doc(TypeDocEnum.CPF, "48118530541"));

        assertEquals(result.getAgency().getId(), 1);
        assertEquals(result.getPhones(), phones);
        assertEquals(result.getDocs(), docs);
        assertEquals(result.getImobId(), 1);
        assertNull(result.getEmail());
        assertEquals(result.getName(), "Teste");
        assertEquals(result.getNationality(), "");
        assertEquals(result.getRefimob(), "947819A");
    }
}