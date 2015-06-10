package org.me.server.model.dto;


public class Feed {
//    private Id feedId;
//    private Id ownerId;
    private String content;
    private TimeStamp timeStamp;
    private Privacy privacy;

//    public Feed(String content, Privacy privacy) {
//        this(new Id(), new Id(), content, privacy);
//    }

    public Feed(/*Id feedId, Id ownerId, */String content, Privacy privacy) {
//        this.feedId = feedId;
//        this.ownerId = ownerId;
        this.content = content;
        this.privacy = privacy;
    }

//    public Id getFeedId() {
//        return feedId;
//    }
//
//    public void setFeedId(Id feedId) {
//        this.feedId = feedId;
//    }
//
//    public Id getOwnerId() {
//        return ownerId;
//    }
//
//    public void setOwnerId(Id ownerId) {
//        this.ownerId = ownerId;
//    }

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
