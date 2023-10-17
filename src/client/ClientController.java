package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientController {
    private final Client client;
    private final BufferedReader reader;

    public ClientController() throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("[System]: Введите ваше имя пользователя");
        System.out.print("[System]: ");
        String username = reader.readLine();
        this.client = new Client(username, "127.0.0.1", 6666);
        client.start();
        startDialogue();
    }

    private void startDialogue() throws IOException {
        while(true) {
            String message = reader.readLine();
            client.sendMessage(message);
        }
    }
}
