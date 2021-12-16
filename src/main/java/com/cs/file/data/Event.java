package com.cs.file.data;

public class Event {
    private final String id;
    private final long duration;
    private final boolean alert;
    private final String type;
    private final String host;

    public Event(String id, long duration, boolean alert, String type, String host) {
        this.id = id;
        this.duration = duration;
        this.alert = alert;
        this.type = type;
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public long getDuration() {
        return duration;
    }

    public boolean isAlert() {
        return alert;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }
}
