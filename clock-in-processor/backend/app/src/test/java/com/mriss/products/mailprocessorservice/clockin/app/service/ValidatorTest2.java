package com.mriss.products.mailprocessorservice.clockin.app.service;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"ALLOWED_SENDER_LIST=myemail@foo.bar.com,myemail1@foo.bar.com"})
class ValidatorTest2 {

    @Autowired
    Validator validator;

    @Test
    void testValidateOk() {
        Set<String> set = new HashSet<String>();
        set.add("myemail1@foo.bar.com");
        assertTrue(validator.validate("/_ah/mail/myfakeaccount@my-service-name-dot-my-project-id.appspotmail.com",
                set));
    }

}
