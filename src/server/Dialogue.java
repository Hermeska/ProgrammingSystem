package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dialogue extends Thread {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final List<Message> messages;

    private String receiver;
    private final String sender;

    public Dialogue(Socket socket, String sender) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.messages = new ArrayList<>();
        this.sender = sender;

        this.register();
    }

    private void register() throws IOException {
        this.receiver = this.in.readLine();
        this.out.println(sender);
    }

    public String getReceiver() {
        return receiver;
    }

    public void sendMessage(String message) {
        this.out.println(message);
        this.messages.add(new Message(sender, message));
    }

    public void receiveMessage(String message) {
        Message msg = new Message(receiver, message);
        this.messages.add(msg);
        System.out.println(msg);
    }

    public String getDialog() {
        return messages
                .stream()
                .map(Message::toString)
                .collect(Collectors.joining("\n"));
    }

    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                if (MessageStatus.fromString(message) != null) {
                    System.out.println("\n[System]: Сообщение отправлено");
                    continue;
                }
                receiveMessage(message);
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
