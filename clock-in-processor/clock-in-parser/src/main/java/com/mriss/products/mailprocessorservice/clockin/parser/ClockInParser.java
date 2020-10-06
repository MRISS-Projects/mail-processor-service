package com.mriss.products.mailprocessorservice.clockin.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.NodeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mriss.products.mailprocessorservice.api.MailContentParser;

public class ClockInParser implements MailContentParser<List<Element>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClockInParser.class);

    public static final String DATE_MARKER = "Jornada:";
    public static final String USER_MARKER = "Colaborador:";

    @Override
    public List<Element> parse(InputStream contentSource) {
        String cleanSource = getCleanSource(contentSource);
        LOGGER.debug("cleanSource: " + cleanSource);
        List<Element> result = new ArrayList<Element>();
        if (cleanSource != null) {
            Document doc = Jsoup.parse(cleanSource);
            doc.filter(new NodeFilter() {

                @Override
                public FilterResult head(Node node, int depth) {
                    if (node instanceof Element) {
                        Element el = (Element) node;
                        if (el.tag() == Tag.valueOf("h3")) {
                            if (el.ownText().indexOf(USER_MARKER) != -1) {
                                result.add(el);
                            }
                        } else if (el.tag() == Tag.valueOf("p")) {
                            if (el.ownText().indexOf(DATE_MARKER) != -1) {
                                result.add(el);
                            }
                        } else if (el.tag() == Tag.valueOf("table")) {
                            if (el.parent().tag() == Tag.valueOf("td")) {
                                result.add(el);
                                return FilterResult.SKIP_ENTIRELY;
                            }
                        }
                    }
                    return FilterResult.CONTINUE;
                }

                @Override
                public FilterResult tail(Node node, int depth) {
                    return FilterResult.CONTINUE;
                }

            });
        }
        return result;
    }

    private String getCleanSource(InputStream contentSource) {
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(contentSource));
                    StringWriter writer = new StringWriter()) {
                String line;
                StringBuffer newLine = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    line = line.replaceAll("3D", "");
                    if (line.endsWith("=")) {
                        line = line.substring(0, line.lastIndexOf('='));
                        newLine.append(line);
                        continue;
                    }
                    newLine.append(line).append("\n");
                    writer.write(newLine.toString());
                    newLine = new StringBuffer();
                }
                writer.flush();
                return writer.toString();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

}
