package br.com.kenlo.digitalrentmigrationperson.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import br.com.kenlo.digitalrentmigrationperson.dto.VerifyPersonDTO;

@RunWith(Parameterized.class)
public class PersonUtilTest {

    private PersonUtil personUtil;

    private String email;
    private Integer imobId;
    private Boolean isBroker;
    private Boolean expectedValue;

    public PersonUtilTest(String email, Integer imobId, Boolean isBroker, Boolean expectedValue) {
        this.email = email;
        this.imobId = imobId;
        this.isBroker = isBroker;
        this.expectedValue = expectedValue;
    }

    @Before
    public void setup() {
        personUtil = new PersonUtil();
        personUtil.clearPersons();
    }

    @Parameterized.Parameters
    public static Collection<Object> inputs() {
        return Arrays.asList(new Object[][] {
            { "teste@teste.com.br", 19481, false, false },
            { "aline@teste.com.br", 29999, false, false},
            { "jose@teste.com.br", 11515, false, true },
            { "jose@teste.com.br", 11515, true, false },
            { "jos2e@teste.com.br", 212341, false, true },
            { "", null, false, false },
            { "hamiltonvendas@terra.com.br", 2940161, false, true },
            { "hamiltonvendas@terra.com.br", null, false, true },
            { "", 2905142, false, true },
        });
    }

    @Test
    public void shouldValidateIfPersonAlreadyExist() {
        personUtil.setPerson(VerifyPersonDTO.builder().email("jose@teste.com.br").imobId(1).isBroker(false).build());
        personUtil.setPerson(VerifyPersonDTO.builder().email("teste2@teste.com.br").imobId(212341).isBroker(false).build());
        personUtil.setPerson(VerifyPersonDTO.builder().email("hamiltonvendas@terra.com.br").imobId(2905142).isBroker(false).build());

        VerifyPersonDTO person = VerifyPersonDTO.builder()
                                    .email(email)
                                    .imobId(imobId)
                                    .isBroker(isBroker)
                                    .build();

        assertEquals(expectedValue, personUtil.personAlreadyExist(person));
    }
}