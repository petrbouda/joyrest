package org.joyrest.examples.combiner.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Error {

    List<String> messages = new ArrayList<>();

    public Error() {
    }

    public Error(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Error error = (Error) obj;

        return Objects.equals(this.messages, error.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messages);
    }

}
