package org.me.server.model.dto;

import java.io.Serializable;


public class Post implements Comparable, Serializable {
    private String id;
    private String owner;
    private String text;
    private String time;
    private Privacy privacy;

    public Post(String owner, String text, String time, String id, boolean isPublic) {
        this.id = id;
        this.time = time;

        this.owner = owner;
        this.text = text;
        if (isPublic)
            this.privacy = Privacy.PUBLIC;
        else
            this.privacy = Privacy.FOLLOWERS_ONLY;
    }



    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    @Override
    public String toString() {
        return "<" + time + "> " + owner + ": \"" + text + "\" ";
    }

    @Override
    public int compareTo(Object o) {
        return this.getTime().compareTo( ((Post) o).getTime());
    }

}
