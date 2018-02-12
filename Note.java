package com.zacharyrmckee.notecollector;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ZacharyRMcKee on 2/11/2018.
 */

public class Note implements Comparable<Note>, Serializable {
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

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", lastUpdated=" + lastUpdated +
                '}';
    }

    public Note() {
        this.title = "";
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

    @Override
    public int compareTo(@NonNull Note note) {
        return note.getLastUpdated().compareTo(getLastUpdated());
    }
}
