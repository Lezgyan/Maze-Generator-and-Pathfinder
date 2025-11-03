package maze.generate;

import maze.Cell;
import maze.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenerationPrim implements Generation {

    private static final int MAX_SIZE = 3000;
    private static final Random RANDOM = new Random();

    private static final int[][] DIRECTIONS = {{-2, 0}, {2, 0}, {0, -2}, {0, 2}};

    private Maze initMaze(int width, int height) {

        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("width and height must be >= 1");
        }

        if (width > MAX_SIZE || height > MAX_SIZE) {
            throw new IllegalArgumentException("the size of the labyrinth should not be more than 3000x3000");
        }

        List<List<Cell>> grid = new ArrayList<>(width);
        for (int i = 0; i < width; i++) {
            grid.add(new ArrayList<>(Collections.nCopies(height, Cell.WALL)));
        }
        return new Maze(grid);
    }

    @Override
    public Maze generate(int width, int height) {
        Maze maze = initMaze(width, height);

        int[] randomCell = {RANDOM.nextInt(height), RANDOM.nextInt(width)};
        maze.grid().get(randomCell[0]).set(randomCell[1], Cell.ROAD);

        List<int[]> frontier = new ArrayList<>(frontier(randomCell, maze));

        while (!frontier.isEmpty()) {
            int pickedIndex = RANDOM.nextInt(frontier.size());
            int[] cell = frontier.remove(pickedIndex);
            maze.grid().get(cell[0]).set(cell[1], Cell.ROAD);

            connectRandomNeighbor(cell, maze);
            frontier.addAll(frontier(cell, maze));
        }

        return setWalls(maze);
    }

    private static List<int[]> frontier(int[] cell, Maze maze) {
        List<int[]> neighbors = new ArrayList<>();

        for (int[] dir : DIRECTIONS) {
            int nx = cell[0] + dir[0];
            int ny = cell[1] + dir[1];
            if (isLegal(nx, ny, maze) && maze.grid().get(nx).get(ny) == Cell.WALL) {
                neighbors.add(new int[] {nx, ny});
            }
        }
        return neighbors;
    }

    private static boolean isLegal(int x, int y, Maze maze) {
        return x > 0 && x < maze.height() - 1 && y > 0 && y < maze.width() - 1;
    }

    private static void connectRandomNeighbor(int[] cell, Maze maze) {
        List<int[]> neighbors = neighbors(cell, maze);
        if (!neighbors.isEmpty()) {
            int pickedIndex = RANDOM.nextInt(neighbors.size());
            int[] neighbor = neighbors.get(pickedIndex);

            int[] between = between(cell, neighbor);
            maze.grid().get(between[0]).set(between[1], Cell.ROAD);
        }
    }

    private static List<int[]> neighbors(int[] cell, Maze maze) {
        List<int[]> neighbors = new ArrayList<>();

        for (int[] dir : DIRECTIONS) {
            int nx = cell[0] + dir[0];
            int ny = cell[1] + dir[1];
            if (isLegal(nx, ny, maze) && maze.grid().get(nx).get(ny) == Cell.ROAD) {
                neighbors.add(new int[] {nx, ny});
            }
        }
        return neighbors;
    }

    private static int[] between(int[] p1, int[] p2) {
        int x = (p1[0] + p2[0]) / 2;
        int y = (p1[1] + p2[1]) / 2;
        return new int[] {x, y};
    }

    private static Maze setWalls(Maze maze) {
        int rows = maze.height();
        int cols = maze.width();

        for (int col = 0; col < cols; col++) {
            maze.grid().getFirst().set(col, Cell.WALL);
            maze.grid().getLast().set(col, Cell.WALL);
        }

        for (int row = 0; row < rows; row++) {
            maze.grid().get(row).set(0, Cell.WALL);
            maze.grid().get(row).set(cols - 1, Cell.WALL);
        }
        return maze;
    }
}
