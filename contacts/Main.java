package contacts;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String filename = args.length > 0 ? args[0] : null;
        App app = new App(filename);
        while (true) {
            app.menu();
        }
    }
}
