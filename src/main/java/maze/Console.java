package maze;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import jline.console.ConsoleReader;

public class Console {

    private final PrintStream printStream;

    private final Scanner scanner;

    private final ConsoleReader consoleReader;

    public Console(InputStream inputStream, PrintStream printStream) {
        this.printStream = printStream;
        this.scanner = new Scanner(inputStream);
        try {
            this.consoleReader = new ConsoleReader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void print(String str) {
        printStream.print(str);
    }

    public void println(String str) {
        printStream.println(str);
    }

    public void println() {
        printStream.println();
    }

    public String read() {
        return scanner.nextLine();
    }

    public void clear() throws IOException {
        consoleReader.clearScreen();
    }

}
