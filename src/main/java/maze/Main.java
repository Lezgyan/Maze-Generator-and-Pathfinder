package maze;

import java.io.IOException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) throws IOException {
        Game main = new Game();
        main.start();
    }
}
