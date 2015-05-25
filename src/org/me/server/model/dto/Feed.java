package org.me.server.model.dto;


public class Feed {
    private String feedId;
    private String ownerId;
    private String content;
    private Privacy privacy;

    public Feed(String feedId, String ownerId, String content, Privacy privacy) {
        this.feedId = feedId;
        this.ownerId = ownerId;
        this.content = content;
        this.privacy = privacy;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }
}
