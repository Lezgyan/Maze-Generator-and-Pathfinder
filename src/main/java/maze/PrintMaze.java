package maze;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrintMaze {

    private final Console console;

    public void print(Maze maze) {
        for (int i = 0; i < maze.height(); i++) {
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < maze.width(); j++) {
                s.append(maze.grid().get(i).get(j).color());
            }
            console.println(new String(s));
        }
    }

    public void print(Maze maze, List<Coordinate> path) {
        if (path.isEmpty()) {
            console.println("Path not found");
            return;
        }
        Map<Coordinate, Cell> pathColors = pathColors(path);
        for (int i = 0; i < maze.height(); i++) {
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < maze.width(); j++) {
                Cell a = pathColors.get(new Coordinate(j, i));
                if (a != null && maze.grid().get(i).get(j) == Cell.ROAD) {
                    s.append(a.color());
                } else {
                    s.append(maze.grid().get(i).get(j).color());
                }
            }
            console.println(new String(s));
        }
    }

    private Map<Coordinate, Cell> pathColors(List<Coordinate> path) {
        Map<Coordinate, Cell> result = new HashMap<>();

        result.put(path.getFirst(), Cell.START);
        for (int i = 1; i < path.size() - 1; i++) {
            result.put(path.get(i), Cell.SHORT_PATH);
        }
        result.put(path.getLast(), Cell.FINISH);
        return result;
    }
}
