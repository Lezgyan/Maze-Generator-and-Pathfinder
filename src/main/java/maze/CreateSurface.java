package maze;

import java.util.Random;

public class CreateSurface {
    private final static Random RANDOM = new Random();

    public final static Double GENERATION_COEFFICIENT_GOOD = 0.03;
    public final static Double GENERATION_COEFFICIENT_BAD = 0.03;

    public void create(Maze maze) {
        int cntGood = (int) (maze.height() * maze.width() * GENERATION_COEFFICIENT_GOOD);

        while (cntGood > 0) {
            int row = RANDOM.nextInt(1, maze.height() - 1);
            int column = RANDOM.nextInt(1, maze.width() - 1);
            if (maze.grid().get(row).get(column) != Cell.WALL && maze.grid().get(row).get(column) != Cell.ROAD_GOOD) {
                maze.grid().get(row).set(column, Cell.ROAD_GOOD);
                cntGood--;
            }
        }

        int cntBad = (int) (maze.height() * maze.width() * GENERATION_COEFFICIENT_BAD);

        while (cntBad > 0) {
            int row = RANDOM.nextInt(1, maze.height() - 1);
            int column = RANDOM.nextInt(1, maze.width() - 1);
            if (maze.grid().get(row).get(column) != Cell.WALL && maze.grid().get(row).get(column) != Cell.ROAD_GOOD
                && maze.grid().get(row).get(column) != Cell.ROAD_BAD) {
                maze.grid().get(row).set(column, Cell.ROAD_BAD);
                cntBad--;
            }
        }
    }

}
