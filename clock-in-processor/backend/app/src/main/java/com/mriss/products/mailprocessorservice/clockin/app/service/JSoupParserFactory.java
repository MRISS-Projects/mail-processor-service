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

import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.mriss.products.mailprocessorservice.api.dto.ClockinEntry;
import com.mriss.products.mailprocessorservice.api.dto.ClockinRecordDto;
import com.mriss.products.mailprocessorservice.api.dto.SingleRecord;
import com.mriss.products.mailprocessorservice.clockin.parser.JsoupClockInParser;

@Service
public class JSoupParserFactory implements ClockinMailParserFactory<JsoupClockInParser> {

    @Override
    public JsoupClockInParser getNewMailParser() {
        return new JsoupClockInParser();
    }

    @Override
    public ClockinRecordDto getRecordsFromContent(JsoupClockInParser parser, String sender) {
        List<Element> elements = parser.getElements();
        ClockinRecordDto dto = getDtoFromElements(elements, sender);
        return dto;
    }

    private ClockinRecordDto getDtoFromElements(List<Element> elements, String sender) {
        ClockinRecordDto dto = new ClockinRecordDto();
        ClockinEntry entry = null;
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            if (element.tag() == Tag.valueOf("h3")) {
                dto.setUser(parseUser(element.ownText()));
                dto.setUserEmail(sender);
            } else if (element.tag() == Tag.valueOf("p")) {
                entry = new ClockinEntry();
                entry.setDate(parseDate(element.ownText()));
                dto.addEntry(entry);
            } else if (element.tag() == Tag.valueOf("table")) {
                Elements records = getRecords(element);
                SingleRecord singleRecord = null;
                for (int i = 0; i < records.size(); i++) {
                    String value = records.get(i).getElementsByTag("td").get(1).ownText().trim();
                    if (i % 2 == 0) {
                        singleRecord = new SingleRecord();
                        singleRecord.setClockIn(value);
                        entry.addRecord(singleRecord);
                    } else {
                        singleRecord.setClockOut(value);
                    }
                }
            }
        }
        return dto;
    }

    private Elements getRecords(Element element) {
        Elements elements = element.getElementsByTag("tbody");
        Element tbody = elements.get(0);
        return tbody.getElementsByTag("tr");
    }

    private String parseDate(String ownText) {
        return ownText.substring(JsoupClockInParser.DATE_MARKER.length() + 1);
    }

    private String parseUser(String ownText) {
        return ownText.substring(JsoupClockInParser.USER_MARKER.length() + 1);
    }

}
