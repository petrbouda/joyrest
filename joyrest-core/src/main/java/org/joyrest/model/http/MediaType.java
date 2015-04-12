package org.joyrest.model.http;

import org.joyrest.exception.type.RestException;

import java.util.Objects;

import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;

public final class MediaType {

    public static final MediaType DEFAULT_ACCEPT_MEDIA_TYPE = MediaType.JSON;

    public static final MediaType FORM_URLENCODED = new MediaType("application", "x-www-form-urlencoded");
    public static final MediaType JSON = new MediaType("application", "json");
    public static final MediaType OCTET_STREAM = new MediaType("application", "octet-stream");
    public static final MediaType MULTIPART_FORM_DATA = new MediaType("multipart", "form-data");
    public static final MediaType HTML = new MediaType("text", "html");
    public static final MediaType PLAIN = new MediaType("text", "plain");
    public static final MediaType WILDCARD = new MediaType("*", "*");

    public static final MediaType XML = new MediaType("application", "xml");
    public static final MediaType TEXT_XML = new MediaType("text", "xml");
    public static final MediaType ATOM_XML = new MediaType("application", "atom+xml");
    public static final MediaType XHTML_XML = new MediaType("application", "xhtml+xml");

    private final String type;

    private final String subType;

    private MediaType(String type, String subType) {
        this.type = type;
        this.subType = subType;
    }

    public static MediaType of(String mediaType) {
        if(mediaType == null || mediaType.isEmpty()) {
            return WILDCARD;
        }

        String[] typeSplit = mediaType.split("/");
        if(typeSplit.length != 2) {
            throw internalServerErrorSupplier().get();
        }

        // TODO Caching values?
        return new MediaType(typeSplit[0], typeSplit[1]);
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
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
        return Objects.equals(this.type.toLowerCase(), other.type.toLowerCase());
    }

    @Override
    public String toString() {
        return type;
    }
}

