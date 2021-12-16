package com.cs.file.data;

public class StringToEventConverter {
    public EventData getEventData(String line) {
        String[] tokens = line.split(",");
        if (tokens.length >= 3 && tokens.length <= 5) {
            String id = parseString(tokens[0]);
            String state = parseString(tokens[1]);
            String timestamp = null;
            String type = null;
            String host = null;

            if (tokens[2].contains("timestamp")) {
                timestamp = parseString(tokens[2].substring(0, tokens[2].indexOf('}')));
            } else if (tokens[2].contains("type")) {
                type = parseString(tokens[2]);
                host = parseString(tokens[3]);
                timestamp = parseString(tokens[4].substring(0, tokens[4].indexOf('}')));
            }
            return new EventData(
                    id,
                    State.STARTED.equals(state) ? State.STARTED : State.FINISHED,
                    type,
                    host,
                    Long.parseLong(timestamp)
            );
        }
        return null;
    }

    private String parseString(String token) {
        return token.substring(token.indexOf(':') + 1).trim();
    }
}
