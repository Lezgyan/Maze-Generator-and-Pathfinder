package maze.path;

import maze.Coordinate;
import maze.Maze;
import java.util.List;

public interface PathFinder {

    List<Coordinate> findPath(Maze maze, Coordinate from, Coordinate to);

}
