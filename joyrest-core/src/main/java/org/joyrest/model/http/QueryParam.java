package org.joyrest.model.http;

public class QueryParam extends NameValueEntity<String, String[]> {

    public QueryParam(String name, String... value) {
        super(name, value);
    }

}
