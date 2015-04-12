package org.joyrest.model.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.joyrest.exception.type.RestException.internalServerErrorSupplier;

public final class MediaType {

    public static final MediaType FORM_URLENCODED = new MediaType("application", "x-www-form-urlencoded");
    public static final MediaType JSON = new MediaType("application", "json");
    public static final MediaType OCTET_STREAM = new MediaType("application", "octet-stream");
    public static final MediaType MULTIPART_FORM_DATA = new MediaType("multipart", "form-data");
    public static final MediaType HTML = new MediaType("text", "html");
    public static final MediaType PLAIN = new MediaType("text", "plain");
    public static final MediaType WILDCARD = new MediaType("*", "*");

    public static final MediaType TEXT_XML = new MediaType("text", "xml");
    public static final MediaType XML = new MediaType("application", "xml");
    public static final MediaType ATOM_XML = new MediaType("application", "atom+xml", XML);
    public static final MediaType XHTML_XML = new MediaType("application", "xhtml+xml", XML);

    private static final Map<String, MediaType> BASIC_MEDIA_TYPE;

    static {
        BASIC_MEDIA_TYPE = new HashMap<>();
        BASIC_MEDIA_TYPE.put("json", JSON);
        BASIC_MEDIA_TYPE.put("xml", XML);
    }

    private final String type;

    private final String subType;

    private Optional<MediaType> processingType;

    private MediaType(String type, String subType) {
        this(type, subType, null);
    }

    private MediaType(String type, String subType, MediaType processingType) {
        this.type = type;
        this.subType = subType;
        this.processingType = Optional.ofNullable(processingType);
    }

    public static MediaType of(String mediaType) {
        if(mediaType == null || mediaType.isEmpty()) {
            return WILDCARD;
        }

        String[] typeSplit = mediaType.split("/");
        if(typeSplit.length != 2) {
            throw internalServerErrorSupplier().get();
        }

        MediaType processingType = null;
        if(typeSplit[1].contains("+")) {
            String[] processingSplit = typeSplit[1].split("\\+");
            if(processingSplit.length == 2) {
                processingType = BASIC_MEDIA_TYPE.get(processingSplit[1]);
            }
        }

        return new MediaType(typeSplit[0], typeSplit[1], processingType);
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public Optional<MediaType> getProcessingType() {
        return processingType;
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
        return type + "/" + subType;
    }
}

