package org.me.server.model.dto;


public class TimeStamp {
    String val;

    public TimeStamp(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }
}
