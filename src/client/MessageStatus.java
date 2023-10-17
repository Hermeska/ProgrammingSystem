package client;

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
}
