package maze.generate;

import maze.Cell;
import maze.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenerationEuler implements Generation {

    private static final Random RANDOM = new Random();
    private static final Integer WALL_GENERATION_NUMBER = 3;
    private int width;
    private int height;
    private int outputHeight;
    private int outputWidth;
    private List<List<Cell>> maze;
    private List<Integer> rowSet;
    private int set;
    private static final int MAX_SIZE = 3000;

    private void init(int width, int height) {

        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("width and height must be >= 1");
        }

        if (width > MAX_SIZE || height > MAX_SIZE) {
            throw new IllegalArgumentException("the size of the labyrinth should not be more than 3000x3000");
        }

        this.width = width;
        this.height = height;
        this.outputHeight = height * 2 + 1;
        this.outputWidth = width * 2 + 1;
    }

    @Override
    public Maze generate(int width, int height) {
        init(width / 2, height / 2);
        initializeMazeGrid();
        initializeRowSet();
        set = 1;

        for (int i = 0; i < height / 2; ++i) {
            assignInitialSetNumbers();
            processRightWalls(i);
            processBottomWalls(i);

            if (i != (height / 2) - 1) {
                ensureVerticalConnections(i);
            }
        }

        finalizeMaze();
        if (width % 2 == 0) {
            for (int i = 0; i < maze.size(); i++) {
                maze.get(i).removeLast();
            }
            for (int i = 0; i < maze.size(); i++) {
                maze.get(i).set(maze.getFirst().size() - 1, Cell.WALL);
            }
        }

        if (height % 2 == 0) {
            maze.removeLast();
            for (int i = 0; i < maze.getFirst().size(); i++) {
                maze.getLast().set(i, Cell.WALL);
            }
        }
        return new Maze(maze);
    }

    private void initializeMazeGrid() {
        maze = new ArrayList<>(outputHeight);
        for (int i = 0; i < outputHeight; ++i) {
            List<Cell> row = new ArrayList<>(outputWidth);
            for (int j = 0; j < outputWidth; ++j) {
                if (i % 2 == 1 && j % 2 == 1) {
                    row.add(Cell.ROAD);
                } else if ((i % 2 == 1 && j % 2 == 0 && j != 0 && j != outputWidth - 1)
                    || (j % 2 == 1 && i % 2 == 0 && i != 0 && i != outputHeight - 1)) {
                    row.add(Cell.ROAD);
                } else {
                    row.add(Cell.WALL);
                }
            }
            maze.add(row);
        }
    }

    private void initializeRowSet() {
        rowSet = new ArrayList<>(Collections.nCopies(width, 0));
    }

    private void assignInitialSetNumbers() {
        for (int j = 0; j < width; ++j) {
            if (rowSet.get(j) == 0) {
                rowSet.set(j, set++);
            }
        }
    }

    private void processRightWalls(int row) {
        for (int j = 0; j < width - 1; ++j) {
            int rightWall = RANDOM.nextInt(WALL_GENERATION_NUMBER);
            if (rightWall == 1 || rowSet.get(j).equals(rowSet.get(j + 1))) {
                maze.get(row * 2 + 1).set(j * 2 + 2, Cell.WALL);
            } else {
                int changingSet = rowSet.get(j + 1);
                for (int l = 0; l < width; ++l) {
                    if (rowSet.get(l) == changingSet) {
                        rowSet.set(l, rowSet.get(j));
                    }
                }
            }
        }
    }

    private void processBottomWalls(int row) {
        for (int j = 0; j < width; ++j) {
            int bottomWall = RANDOM.nextInt(WALL_GENERATION_NUMBER);
            int countCurrentSet = Collections.frequency(rowSet, rowSet.get(j));
            if (bottomWall == 1 && countCurrentSet != 1) {
                maze.get(row * 2 + 2).set(j * 2 + 1, Cell.WALL);
            }
        }
    }

    private void ensureVerticalConnections(int row) {
        for (int j = 0; j < width; ++j) {
            int countHole = 0;
            for (int l = 0; l < width; ++l) {
                if (rowSet.get(l).equals(rowSet.get(j)) && maze.get(row * 2 + 2).get(l * 2 + 1) == Cell.ROAD) {
                    countHole++;
                }
            }
            if (countHole == 0) {
                maze.get(row * 2 + 2).set(j * 2 + 1, Cell.ROAD);
            }
        }

        for (int j = 0; j < width; ++j) {
            if (maze.get(row * 2 + 2).get(j * 2 + 1) == Cell.WALL) {
                rowSet.set(j, 0);
            }
        }
    }

    private void finalizeMaze() {
        for (int j = 0; j < width - 1; ++j) {
            if (!rowSet.get(j).equals(rowSet.get(j + 1))) {
                maze.get(outputHeight - 2).set(j * 2 + 2, Cell.ROAD);
            }
        }
    }
}

