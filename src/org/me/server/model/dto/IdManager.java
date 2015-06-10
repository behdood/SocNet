package org.me.server.model.dto;


public class IdManager {
    private static IdManager instance = null;
    private long currentUserId;
    private long currentFeedId;

    private IdManager() {
        currentUserId = 0;
    }

    public static IdManager getInstance() {
        if (instance == null)
            instance = new IdManager();

        return instance;
    }

    public Id getNextUserId() {
        ++instance.currentUserId;

        return new Id(instance.currentUserId + "");
    }

    public Id getNextFeedId() {
        ++instance.currentFeedId;

        return new Id(instance.currentFeedId + "");
    }
}
