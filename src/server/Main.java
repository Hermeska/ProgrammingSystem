package server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerController controller = new ServerController("Morpheus", 6666);
    }
}
