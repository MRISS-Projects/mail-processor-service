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
    public ClockinRecordDto getRecordsFromContent(JsoupClockInParser parser) {
        List<Element> elements = parser.getElements();
        ClockinRecordDto dto = getDtoFromElements(elements);
        return dto;
    }

    private ClockinRecordDto getDtoFromElements(List<Element> elements) {
        ClockinRecordDto dto = new ClockinRecordDto();
        ClockinEntry entry = null;
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            if (element.tag() == Tag.valueOf("h3")) {
                dto.setUser(parseUser(element.ownText()));
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
