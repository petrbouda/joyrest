package org.joyrest.ittest.routes.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FeedEntry implements Serializable {

    private static final long serialVersionUID = 8612496546494451823L;

    private String title;
    private String link;
    private String description;
    private Date publishDate;

    public FeedEntry() {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
