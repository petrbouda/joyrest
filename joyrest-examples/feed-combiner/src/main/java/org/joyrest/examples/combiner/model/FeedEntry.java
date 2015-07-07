package org.joyrest.examples.combiner.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FeedEntry implements Serializable {

    private static final long serialVersionUID = 8612496546494451823L;

    private String title;
    private String link;
    private String description;
    private Date publishDate;

    // for purposes of JAXB
    @SuppressWarnings("unused")
    private FeedEntry() {
    }

    public FeedEntry(String title, String link, String description, Date publishDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("title", title)
            .add("link", link)
            .add("description", description)
            .add("publishDate", publishDate)
            .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FeedEntry entry = (FeedEntry) obj;

        return Objects.equal(this.title, entry.title)
            && Objects.equal(this.link, entry.link)
            && Objects.equal(this.description, entry.description)
            && Objects.equal(this.publishDate, entry.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, link, description, publishDate);
    }
}
