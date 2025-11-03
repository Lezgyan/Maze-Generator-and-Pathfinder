package maze.path;

import maze.Cell;
import maze.Coordinate;
import maze.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import static maze.Cell.ROAD;
import static maze.Cell.WALL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BfsPathFinderTest {
    public static final List<List<Cell>> maze = List.of(
        List.of(WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL),
        List.of(WALL, ROAD, WALL, ROAD, ROAD, ROAD, ROAD, ROAD, WALL, ROAD, ROAD, ROAD, WALL, ROAD, ROAD, ROAD, WALL),
        List.of(WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, WALL, WALL, ROAD, WALL, ROAD, WALL),
        List.of(WALL, ROAD, ROAD, ROAD, WALL, ROAD, WALL, ROAD, ROAD, ROAD, ROAD, ROAD, ROAD, ROAD, WALL, ROAD, WALL),
        List.of(WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, WALL, WALL, ROAD, WALL, ROAD, WALL, WALL, WALL),
        List.of(WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, ROAD, ROAD, WALL, ROAD, WALL, ROAD, ROAD, ROAD, WALL),
        List.of(WALL, ROAD, WALL, WALL, WALL, ROAD, WALL, WALL, WALL, WALL, WALL, ROAD, WALL, WALL, WALL, ROAD, WALL),
        List.of(WALL, ROAD, WALL, ROAD, ROAD, ROAD, ROAD, ROAD, WALL, ROAD, WALL, ROAD, ROAD, ROAD, WALL, ROAD, WALL),
        List.of(WALL, ROAD, WALL, WALL, WALL, WALL, WALL, WALL, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL),
        List.of(WALL, ROAD, WALL, ROAD, WALL, ROAD, ROAD, ROAD, ROAD, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL),
        List.of(WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, WALL, WALL, ROAD, WALL),
        List.of(WALL, ROAD, ROAD, ROAD, ROAD, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, ROAD, ROAD, WALL, ROAD, WALL),
        List.of(WALL, WALL, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, WALL, WALL),
        List.of(WALL, ROAD, ROAD, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, ROAD, ROAD, ROAD, WALL),
        List.of(WALL, ROAD, WALL, ROAD, WALL, ROAD, WALL, WALL, WALL, ROAD, WALL, WALL, WALL, ROAD, WALL, WALL, WALL),
        List.of(WALL, ROAD, WALL, ROAD, WALL, ROAD, ROAD, ROAD, WALL, ROAD, WALL, ROAD, ROAD, ROAD, ROAD, ROAD, WALL),
        List.of(WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL)
    );

    @Test
    void existingPath() {
        // given
        PathFinder finder = new BfsPathFinder();
        Coordinate from = new Coordinate(1, 1);
        Coordinate to = new Coordinate(15, 15);

        List<Coordinate> expectedPath = List.of(
            new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(2, 3),
            new Coordinate(3, 3), new Coordinate(3, 2), new Coordinate(3, 1), new Coordinate(4, 1),
            new Coordinate(5, 1), new Coordinate(6, 1), new Coordinate(7, 1), new Coordinate(7, 2),
            new Coordinate(7, 3), new Coordinate(8, 3), new Coordinate(9, 3), new Coordinate(10, 3),
            new Coordinate(11, 3), new Coordinate(11, 4), new Coordinate(11, 5), new Coordinate(11, 6),
            new Coordinate(11, 7), new Coordinate(11, 8), new Coordinate(11, 9), new Coordinate(11, 10),
            new Coordinate(11, 11), new Coordinate(12, 11), new Coordinate(13, 11), new Coordinate(13, 12),
            new Coordinate(13, 13), new Coordinate(13, 14), new Coordinate(13, 15), new Coordinate(14, 15),
            new Coordinate(15, 15)
        );

        // when
        List<Coordinate> finderPath = finder.findPath(new Maze(maze), from, to);

        // then
        assertThat(finderPath).isEqualTo(expectedPath);
    }

    @Test
    void pathNotFound() {
        // given
        PathFinder finder = new BfsPathFinder();
        Coordinate from = new Coordinate(1, 1);
        Coordinate to = new Coordinate(15, 15);

        List<List<Cell>> mazeCopy = maze.stream()
            .map(ArrayList::new)
            .collect(Collectors.toList());

        mazeCopy.get(8).set(11, WALL);

        Maze mazeWithoutPath = new Maze(mazeCopy);

        // when
        List<Coordinate> finderPath = finder.findPath(mazeWithoutPath, from, to);

        // then
        assertThat(finderPath).isEqualTo(Collections.emptyList());
    }

}
