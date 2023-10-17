package server;

public enum MessageStatus {
    SENT("SENT"),
    RECEIVED("RECEIVED");
    private final String status;
    MessageStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

    public static MessageStatus fromString(String text) {
        for (MessageStatus e : MessageStatus.values()) {
            if (e.status.equalsIgnoreCase(text)) {
                return e;
            }
        }
        return null;
    }
}
