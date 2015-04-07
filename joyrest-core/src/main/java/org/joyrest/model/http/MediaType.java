package org.joyrest.model.http;

import java.util.Objects;

public final class MediaType {

    public static final MediaType DEFAULT_ACCEPT_MEDIA_TYPE = MediaType.JSON;

    public static final MediaType FORM_URLENCODED = new MediaType("application/x-www-form-urlencoded");
    public static final MediaType JSON = new MediaType("application/json");
    public static final MediaType OCTET_STREAM = new MediaType("application/octet-stream");
    public static final MediaType MULTIPART_FORM_DATA = new MediaType("multipart/form-data");
    public static final MediaType HTML = new MediaType("text/html");
    public static final MediaType PLAIN = new MediaType("text/plain");
    public static final MediaType WILDCARD = new MediaType("*/*");

    public static final MediaType XML = new MediaType("application/xml");
    public static final MediaType TEXT_XML = new MediaType("text/xml");
    public static final MediaType ATOM_XML = new MediaType("application/atom+xml");
    public static final MediaType XHTML_XML = new MediaType("application/xhtml+xml");

    private final String value;

    private MediaType(String value) {
        this.value = value;
    }

    public static MediaType of(String mediaType) {
        if(mediaType == null || mediaType.isEmpty()) {
            return WILDCARD;
        }

        // TODO Caching values?
        return new MediaType(mediaType);
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final MediaType other = (MediaType) obj;
        return Objects.equals(this.value.toLowerCase(), other.value.toLowerCase());
    }

    @Override
    public String toString() {
        return value;
    }
}

