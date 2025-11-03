package maze;

import java.util.List;

public record Maze(List<List<Cell>> grid, int height, int width) {
    public Maze(List<List<Cell>> grid) {
        this(grid, grid.size(), grid.getFirst().size());
    }
}
