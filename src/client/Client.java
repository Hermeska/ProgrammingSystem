package client;

import server.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client extends Thread{

    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final String username;
    private String receiver;

    private final List<Message> messages;

    private boolean canSendMessage = false;

    public Client(String username, String ip, int port) throws IOException {
        this.messages = new ArrayList<>();
        this.username = username;
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        register();
    }

    private void register() throws IOException {
        out.println(this.username);
        this.receiver = in.readLine();
        System.out.println("[System]: Успешная регистрация");
    }

    public void sendMessage(String msg) throws IOException {
        if (!canSendMessage) {
            System.out.println("[System]: Невозможно отправить сообщение");
            return;
        }
        out.println(msg);
        Message message = new Message(username, msg);
        this.messages.add(message);
        System.out.println(message);
        canSendMessage = false;
    }

    public void receiveMessage(String message) {
        out.println(MessageStatus.RECEIVED);
        canSendMessage = true;
        Message msg = new Message(receiver, message);
        this.messages.add(msg);
        System.out.println(msg);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                receiveMessage(message);
            }
            stopConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
