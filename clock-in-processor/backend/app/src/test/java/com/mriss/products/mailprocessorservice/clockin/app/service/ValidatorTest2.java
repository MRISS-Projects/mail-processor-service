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

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"ALLOWED_SENDER_LIST=myemail@foo.bar.com,myemail1@foo.bar.com"})
public class ValidatorTest2 {

    @Autowired
    Validator validator;

    @Test
    public void testValidateOk() {
        Set<String> set = new HashSet<String>();
        set.add("myemail1@foo.bar.com");
        assertTrue(validator.validate("/_ah/mail/myfakeaccount@my-service-name-dot-my-project-id.appspotmail.com",
                set));
    }

}
