package br.com.kenlo.digitalrentmigrationperson.processors;

import static org.junit.Assert.assertTrue;
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
import br.com.kenlo.digitalrentmigrationperson.data.source.ImobUser;
import br.com.kenlo.digitalrentmigrationperson.util.PersonUtil;

public class UserProcessorTest {

    @Mock
    private PersonUtil personUtil;

    @InjectMocks
    private UserProcessor processor = new UserProcessor();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldProcessUserWithSuccess() throws Exception {
        when(this.personUtil.personAlreadyExist(any())).thenReturn(false);

        final ImobUser imobClient = ImobUser
                .builder()
                .agencyId(1)
                .cell("(11) 98476-7165")
                .userId(1)
                .creci("1415125125")
                .cpf("481.185.305-41")
                .email("teste@gmail.com")
                .name("Teste")
                .phone("(11) 94857-8471")
                .activate(true)
                .build();

        Persons result = processor.process(imobClient);

        Set<Phone> phones = new HashSet<>();
        phones.add(new Phone(PhoneEnum.LANDLINE, 11948578471L));
        phones.add(new Phone(PhoneEnum.CELL, 11984767165L));

        Set<Doc> docs = new HashSet<>();
        docs.add(new Doc(TypeDocEnum.CRECI, "1415125125"));
        docs.add(new Doc(TypeDocEnum.CPF, "48118530541"));

        assertEquals(result.getAgency().getId(), 1);
        assertEquals(result.getPhones(), phones);
        assertEquals(result.getDocs(), docs);
        assertEquals(result.getImobId(), 1);
        assertEquals(result.getEmail(), "teste@gmail.com");
        assertEquals(result.getName(), "Teste");
        assertTrue(result.getIsActive());

    }

    @Test
    public void shouldClearEmailWhenUserAlreadyExist() throws Exception {
        when(this.personUtil.personAlreadyExist(any())).thenReturn(true);

        final ImobUser imobClient = ImobUser
                .builder()
                .agencyId(1)
                .cell("(11) 98476-7165")
                .userId(1)
                .creci("1415125125")
                .cpf("481.185.305-41")
                .email("teste@gmail.com")
                .name("Teste")
                .phone("(11) 94857-8471")
                .activate(true)
                .build();

        Persons result = processor.process(imobClient);

        Set<Phone> phones = new HashSet<>();
        phones.add(new Phone(PhoneEnum.LANDLINE, 11948578471L));
        phones.add(new Phone(PhoneEnum.CELL, 11984767165L));

        Set<Doc> docs = new HashSet<>();
        docs.add(new Doc(TypeDocEnum.CRECI, "1415125125"));
        docs.add(new Doc(TypeDocEnum.CPF, "48118530541"));

        assertEquals(result.getAgency().getId(), 1);
        assertEquals(result.getPhones(), phones);
        assertEquals(result.getDocs(), docs);
        assertEquals(result.getImobId(), 1);
        assertNull(result.getEmail());
        assertEquals(result.getName(), "Teste");
        assertTrue(result.getIsActive());
    }
}