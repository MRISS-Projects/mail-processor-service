package com.mriss.products.mailprocessorservice.api;

import java.io.InputStream;

public interface MailContentParser<T> {

    public T parse(InputStream contentSource);

    public int getParsedElementsSize();

}
