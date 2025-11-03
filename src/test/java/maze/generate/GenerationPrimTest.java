package maze.generate;

import maze.Cell;
import maze.Maze;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenerationPrimTest {

    @ParameterizedTest
    @MethodSource("getNegativeSizes")
    void checkPositive(Integer x, Integer y) {
        // given
        Generation generation = new GenerationPrim();
        // when - then
        assertThatThrownBy(() -> {
            generation.generate(x, y);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("width and height must be >= 1");
    }


    @Test
    public void testMazeBoundariesAreWalls() {
        // given
        Generation generator = new GenerationPrim();
        // when
        Maze maze = generator.generate(30, 30);
        List<List<Cell>> grid = maze.grid();

        // then
        int rows = grid.size();
        int cols = grid.getFirst().size();

        for (int col = 0; col < cols; col++) {
            assertEquals(Cell.WALL, grid.getFirst().get(col));
            assertEquals(Cell.WALL, grid.get(rows - 1).get(col));
        }

        for (int row = 0; row < rows; row++) {
            assertEquals(Cell.WALL, grid.get(row).getFirst());
            assertEquals(Cell.WALL, grid.get(row).get(cols - 1));
        }
    }

    private static List<Arguments> getNegativeSizes() {
        return List.of(
            Arguments.of(-4, 5),
            Arguments.of(5, -4),
            Arguments.of(-4, -5)
        );
    }

}
