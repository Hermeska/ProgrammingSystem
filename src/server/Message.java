package server;

public class Message {
    private final String from;
    private final String message;

    public Message(String from, String message) {
        this.from = from;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("[%s]: %s", from, message);
    }
}
