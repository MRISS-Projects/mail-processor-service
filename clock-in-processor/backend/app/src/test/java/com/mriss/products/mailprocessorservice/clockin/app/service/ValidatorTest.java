/*******************************************************************************
 * Copyright 2020 Marcelo Riss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.mriss.products.mailprocessorservice.clockin.app.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidatorTest {

    @Autowired
    Validator validator;

    @Test
    public void testValidateOk() {
        Set<String> set = new HashSet<String>();
        set.add("myemail@foo.bar.com");
        assertTrue(validator.validate("/_ah/mail/myfakeaccount@my-service-name-dot-my-project-id.appspotmail.com",
                set));
    }

    @Test
    public void testValidateOkInvalidEmail() {
        Set<String> set = new HashSet<String>();
        set.add("email@test.com");
        assertFalse(validator.validate("/_ah/mail/myfakeaccount@my-service-name-dot-my-project-id.appspotmail.com",
                set));
    }

    @Test
    public void testValidateInvalid() {
        Set<String> set = new HashSet<String>();
        set.add("email@test.com");
        assertFalse(validator.validate("/_ah/mail/otheraccount@my-service-name-dot-my-project-id.appspotmail.com",
                set));
    }

}
