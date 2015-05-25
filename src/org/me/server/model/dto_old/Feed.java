package org.me.server.model.dto_old;

import java.io.Serializable;


public class Feed implements Comparable, Serializable {
    private String post_id;
    private String owner;
    private String text;
    private String time;
    private Privacy privacy;

    public Feed(String owner, String text, String time, String post_id, boolean isPublic) {
        this.post_id = post_id;
        this.time = time;

        this.owner = owner;
        this.text = text;
        if (isPublic)
            this.privacy = Privacy.PUBLIC;
        else
            this.privacy = Privacy.FOLLOWERS_ONLY;
    }



    public String getPost_id() {
        return post_id;
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
        return this.getTime().compareTo(((Feed) o).getTime());
    }

}
