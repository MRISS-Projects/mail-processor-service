package com.mriss.products.mailprocessorservice.clockin.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mriss.products.mailprocessorservice.api.MailContentParser;

public class ClockInParser implements MailContentParser<List<Element>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClockInParser.class);

    @Override
    public List<Element> parse(InputStream contentSource) {
        String cleanSource = getCleanSource(contentSource);
        LOGGER.debug("cleanSource: " + cleanSource);
        if (cleanSource != null) {
            try (StringReader cleanSourceReader = new StringReader(cleanSource)) {

            }
        }
        return null;
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
            e.printStackTrace();
            return null;
        }
    }

}
