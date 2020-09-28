package com.mriss.products.mailprocessorservice.clockin.app.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ValidatorTest {

    @Autowired
    Validator validator;

    @Test
    void testValidateOk() {
        Set<String> set = new HashSet<String>();
        set.add("myemail@foo.bar.com");
        assertTrue(validator.validate("/_ah/mail/myfakeaccount@my-service-name-dot-my-project-id.appspotmail.com",
                set));
    }

    @Test
    void testValidateOkInvalidEmail() {
        Set<String> set = new HashSet<String>();
        set.add("email@test.com");
        assertFalse(validator.validate("/_ah/mail/myfakeaccount@my-service-name-dot-my-project-id.appspotmail.com",
                set));
    }

    @Test
    void testValidateInvalid() {
        Set<String> set = new HashSet<String>();
        set.add("email@test.com");
        assertFalse(validator.validate("/_ah/mail/otheraccount@my-service-name-dot-my-project-id.appspotmail.com",
                set));
    }

}
