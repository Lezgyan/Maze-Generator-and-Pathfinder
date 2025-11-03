package maze;

import lombok.experimental.UtilityClass;
import maze.exceptions.InputReadException;
import java.util.List;

@UtilityClass
public final class InputReader {

    private final static String INVALID_INPUT_MESSAGE =
        "Invalid input. Please enter two integers separated by space.";

    public static Coordinate readCoordinate(Maze maze, String line) {
        String[] strings = line.trim().split(" ");

        if (strings.length != 2) {
            throw new InputReadException(INVALID_INPUT_MESSAGE);
        }

        Integer x = readNumber(strings[0]);
        Integer y = readNumber(strings[1]);

        if (x == null || y == null) {
            throw new InputReadException(INVALID_INPUT_MESSAGE);
        }

        if (x >= 0 && x < maze.width() && y >= 0 && y < maze.height()) {
            if (maze.grid().get(x).get(y) != Cell.WALL) {
                return new Coordinate(x, y);

            } else {
                throw new InputReadException(
                    "Coordinate (" + x + ", " + y + ") is a WALL. Please choose a different coordinate."
                );
            }
        } else {
            throw new InputReadException(
                "Coordinates out of maze bounds. Maze size is %s x %s. Please try again.".formatted(
                    maze.width(),
                    maze.height()
                )
            );
        }
    }

    public static Integer readIndex(List<String> algorithms, String line) {
        Integer number = InputReader.readNumber(line);
        if (number != null) {
            if (number > 0 && number <= algorithms.size()) {
                return number;
            } else {
                throw new InputReadException(
                    "Invalid choice. Please select a number between 1 and " + algorithms.size()
                );
            }
        } else {
            throw new InputReadException("Invalid input. Please enter a number.");
        }
    }

    public static Integer readNumber(String line) {
        try {
            String num = line.trim();
            return Integer.parseInt(num);
        } catch (Exception e) {
            return null;
        }
    }
}
