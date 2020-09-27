package com.mriss.products.mailprocessorservice.clockin.app.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ValidatorTest {

    @Autowired
    Validator validator;

    @Test
    void testValidateOk() {
        assertTrue(validator.validate("/_ah/mail/myfakeaccount@my-service-name-dot-my-project-id.appspotmail.com",
                new HashSet<String>()));
    }

    @Test
    void testValidateInvalid() {
        assertFalse(validator.validate("/_ah/mail/otheraccount@my-service-name-dot-my-project-id.appspotmail.com",
                new HashSet<String>()));
    }

}
