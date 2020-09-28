package com.mriss.products.mailprocessorservice.clockin.app.service;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class);

    @Value("${ALLOWED_ACCOUNT}")
    private String allowedAccount;

    @Value("${ALLOWED_SENDER_LIST}")
    private String allowedSenderList;

    @Value("${GAE_SERVICE}")
    private String serviceName;

    @Value("${GOOGLE_CLOUD_PROJECT}")
    private String projectId;

    private Pattern pattern;

    private String[] allowedSendersArray;

    public Validator() {
    }

    @PostConstruct
    public void init() {
        StringBuffer sb = new StringBuffer("/_ah/mail/");
        this.pattern = Pattern.compile(sb.append("(").append(allowedAccount).append(")").append("@").append("(")
                .append(serviceName).append(")").append("(\\-dot\\-)").append("(").append(projectId).append(")")
                .append("(").append("\\.appspotmail\\.com").append(")").toString());
        if (allowedSenderList.indexOf(",") != -1) {
            this.allowedSendersArray = allowedSenderList.split(",");
        } else {
            allowedSendersArray = new String[1];
            allowedSendersArray[0] = allowedSenderList;
        }
    }

    public boolean validate(String requestURI, Set<String> set) {
        Matcher m = pattern.matcher(requestURI);
        return m.matches() && areSendersInSendersList(set);
    }

    private boolean areSendersInSendersList(Set<String> set) {
        for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
            String from = (String) iterator.next();
            for (int i = 0; i < allowedSendersArray.length; i++) {
                if (from.indexOf(allowedSendersArray[i]) != -1) {
                    return true;
                }
            }
        }
        return false;
    }

}
