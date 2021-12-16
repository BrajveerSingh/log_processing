package com.cs.file.data;

import java.util.Objects;

public class EventData {
    private final String id;
    private final String state;
    private long timestamp;
    private final String type;
    private final String host;

    public EventData(final String id, final State state, final String type, final String host, final long timestamp) {
        validate(id, state, type, timestamp, host);
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.host = host;
        this.state = state.name();
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    private void validate(String id, State state, String type, long timestamp, String host) {
        if(Objects.isNull(id)){
            throw new IllegalArgumentException("Id cannot be null.");
        }
        if(Objects.isNull(state)){
            throw new IllegalArgumentException("State cannot be null.");
        }
        if(timestamp < 0){
            throw new IllegalArgumentException("timestamp is invalid.");
        }
    }
}
