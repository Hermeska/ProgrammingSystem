package server;

import java.io.*;
import java.util.Set;

public class ServerController {
    private final Server server;
    private final String[] availableCommands;
    private final BufferedReader in;


    ServerController(String name, int port) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.server = new Server(name, port);
        this.availableCommands = new String[]{
                "Посмотреть список онлайн-клиентов",
                "Отпарвить всем сообщение",
                "Отправить личное сообщение"
        };
        this.server.start();
        start();
        //TODO make exp
    }

    private void start() throws IOException {
        try {
            System.out.printf("Добро пожаловать, %s\n", server.getUsername());
            while (true) {
                int ans = showMenu();
                cleanConsole();
                switch (ans) {
                    case 1 -> getClients();
                    case 2 -> sendBroadcastMessage();
                    case 3 -> sendPrivateMessage();
                    default -> System.out.println("Команда не найдена");
                }
                System.out.print("[System]: Нажмите любую кнопку, чтобы вернуться обратно");
                in.readLine();
                cleanConsole();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int showMenu() throws IOException {
        try {
            for (int i = 0; i < this.availableCommands.length; i++) {
                System.out.printf("%d. %s\n", i + 1, this.availableCommands[i]);
            }
            System.out.print("[System]: ");
            return Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(in.readLine());
    }

    private void getClients() throws IOException {
        Set<String> clients = server.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("Клиенты отсутсвуют");
            return;
        }
        for (String client : clients) {
            System.out.println(client);
        }
    }

    private void sendBroadcastMessage() throws IOException {
        try {
            System.out.println("[System]: Введите сообщение для всех пользователей");
            System.out.print("[System]: ");
            String message = in.readLine();
            server.sendBroadcastMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendPrivateMessage() throws IOException {
        try {
            Set<String> clients = server.getAllClients();
            for (String client : clients) {
                System.out.println(client);
            }
            System.out.println("[System]: Веедите имя получателя");
            System.out.print("[System]: ");
            String receiver = in.readLine();
            Dialogue dialogue = server.getDialogue(receiver);
            if (dialogue == null) {
                System.out.println("Клиент с таким именем отсутсвует");
                return;
            }
            System.out.println(dialogue.getDialog());
            System.out.printf("[%s]: ", server.getUsername());
            String message = in.readLine();
            server.sendMessage(receiver, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cleanConsole() {
        System.out.print("\033[H\033[J");
    }

}
