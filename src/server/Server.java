package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Server extends Thread {

    private final ServerSocket serverSocket;
    private final String name;
    private final Map<String, Dialogue> dialogues;

    public Server(String name, int port) throws IOException {
        this.dialogues = new HashMap<>();
        this.name = name;
        serverSocket = new ServerSocket(port);
    }

    public String getUsername() {
        return name;
    }

    public void sendMessage(String receiver, String message) {
        Dialogue dialogue = this.dialogues.get(receiver);
        if (dialogue == null) {
           // throw new UserNotFoundExeption("USER NOT FOUND");
            System.out.println("USER NOT FOUND"); // TODO: PEREDELAT'
        }
        dialogue.sendMessage(message);
    }

    public void sendBroadcastMessage(String message) {
        for (Map.Entry<String, Dialogue> entry: dialogues.entrySet()) {
            entry.getValue().sendMessage(message);
        }
    }

    public Set<String> getAllClients() {
        return dialogues.keySet();
    }

    public Dialogue getDialogue(String receiver) {
        if (!dialogues.containsKey(receiver)) {
            return null;
        }
        return dialogues.get(receiver);
    }

    public void run() {
        while (true) {
            try {
                Dialogue dialogue = new Dialogue(serverSocket.accept(), name);
                String receiver = dialogue.getReceiver();
                dialogues.putIfAbsent(receiver, dialogue);
                dialogue.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
