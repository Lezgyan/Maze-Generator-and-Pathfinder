package maze;

import maze.exceptions.InputReadException;
import maze.generate.Generation;
import maze.generate.GenerationEuler;
import maze.generate.GenerationPrim;
import maze.path.BfsPathFinder;
import maze.path.DijkstraPathFinder;
import maze.path.PathFinder;
import java.io.IOException;
import java.util.List;

public class Game {

    private static final String EXIT = "q";
    private static final List<String> MAZE_GENERATORS_NAMES = List.of(
        "Euler's Algorithm",
        "Prim's Algorithm"
    );

    private static final List<Generation> MAZE_GENERATORS = List.of(
        new GenerationEuler(),
        new GenerationPrim()
    );

    private static final List<String> PATH_FINDERS_NAMES = List.of(
        "Breadth-First Search",
        "Dijkstra Search"
    );

    private static final List<PathFinder> PATH_FINDERS = List.of(
        new BfsPathFinder(),
        new DijkstraPathFinder()
    );

    private final Console console;

    private final PrintMaze printMaze;

    private final CreateSurface createSurface;

    public Game() {
        this.console = new Console(System.in, System.out);
        this.printMaze = new PrintMaze(console);
        this.createSurface = new CreateSurface();
    }

    public void start() throws IOException {
        while (true) {
            game();
            console.println("Press any key to continue or 'q' to exit");
            if (EXIT.equals(console.read())) {
                return;
            }
            console.clear();
        }
    }

    private Integer readAlgorithmIndex(List<String> algorithms) {
        while (true) {
            console.print("Enter the number of your choice: ");
            try {
                int number = InputReader.readIndex(algorithms, console.read()) - 1;
                console.println("You selected: %s".formatted(algorithms.get(number)));
                return number;
            } catch (InputReadException e) {
                console.println(e.getMessage());
            }
        }
    }

    private Coordinate readCoordinate(String message, Maze maze) {
        while (true) {
            try {
                console.print(message);
                return InputReader.readCoordinate(maze, console.read());
            } catch (InputReadException e) {
                console.println(e.getMessage());
            }
        }
    }

    private Integer readSize(String message) {
        while (true) {
            console.print(message);
            Integer number = InputReader.readNumber(console.read());
            if (number == null || number <= 0) {
                console.println("Invalid input. Please enter an integer >= 1.");
                continue;
            }
            return number;
        }
    }

    private void game() {

        console.println("Choose a maze generation algorithm:");
        for (int i = 0; i < MAZE_GENERATORS_NAMES.size(); i++) {
            console.println((i + 1) + ") " + MAZE_GENERATORS_NAMES.get(i));
        }

        Integer generatorIndex = readAlgorithmIndex(MAZE_GENERATORS_NAMES);

        console.println("Choose size of maze: ");

        Integer x = readSize("Enter x: ");
        Integer y = readSize("Enter y: ");

        Maze maze = MAZE_GENERATORS.get(generatorIndex).generate(x, y);

        console.println("Maze generated.");
        printMaze.print(maze);

        console.println("Choose a path-finding algorithm:");
        for (int i = 0; i < PATH_FINDERS_NAMES.size(); i++) {
            console.println((i + 1) + ") " + PATH_FINDERS_NAMES.get(i));
        }

        int pathFinderIndex = readAlgorithmIndex(PATH_FINDERS_NAMES);

        if (pathFinderIndex == 1) {
            createSurface.create(maze);
        }

        Coordinate start = readCoordinate("Enter the start coordinate (x y): ", maze);
        Coordinate finish = readCoordinate("Enter the finish coordinate (x y): ", maze);

        console.println(
            "Finding path from " + start + " to " + finish + " using " + PATH_FINDERS_NAMES.get(pathFinderIndex));

        List<Coordinate> path = PATH_FINDERS.get(pathFinderIndex).findPath(maze, start, finish);

        printMaze.print(maze, path);

    }
}
