package com.zacharyrmckee.notecollector;

import java.util.Date;

/**
 * Created by Starf on 2/11/2018.
 */

public class Note {
    private String title;
    private String text;
    private Date lastUpdated;

    public Note(String title, String text, Date lastUpdated) {
        this.title = title;
        this.text = text;
        this.lastUpdated = lastUpdated;
    }

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        this.lastUpdated = new Date();
    }

    public Note() {
        this.title = "Untitled";
        this.text = "";
        this.lastUpdated = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
