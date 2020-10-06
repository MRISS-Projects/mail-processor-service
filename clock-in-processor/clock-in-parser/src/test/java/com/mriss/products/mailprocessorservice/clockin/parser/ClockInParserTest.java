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
package com.mriss.products.mailprocessorservice.clockin.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClockInParserTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClockInParserTest.class);

    @Test
    public void testParser() throws FileNotFoundException {
        JsoupClockInParser parser = new JsoupClockInParser();
        List<Element> parsingResult = parser.parse(new FileInputStream(new File("target/test-classes/test.txt")));
        assertNotNull(parsingResult);
        assertTrue(parsingResult.size() > 0);
        for (Iterator iterator = parsingResult.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            LOGGER.debug(""+element);
        }
    }

}
