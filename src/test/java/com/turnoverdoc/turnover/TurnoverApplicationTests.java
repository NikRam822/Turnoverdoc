package com.turnoverdoc.turnover;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.iban4j.IbanFormat;
import org.iban4j.IbanUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TurnoverApplicationTests {

	@Test
	void contextLoads() throws NumberParseException {
		IbanUtil.validate(null);
	}

}
