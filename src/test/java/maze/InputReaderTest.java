package maze;

import maze.exceptions.InputReadException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputReaderTest {

    private static final String OUT_OF_MAZE_ERROR = "Coordinates out of maze bounds.";

    private static final String IS_A_WALL_ERROR = "is a WALL";

    private static final String INVALID_INPUT_ERROR =
        "Invalid input. Please enter two integers separated by space.";


    private static Maze createSampleMaze() {
        return new Maze(List.of(
            List.of(Cell.ROAD, Cell.ROAD, Cell.ROAD),
            List.of(Cell.ROAD, Cell.WALL, Cell.ROAD),
            List.of(Cell.ROAD, Cell.ROAD, Cell.ROAD)
        ));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "   1", "1    "})
    void test1(String val) {
        // given
        // when
        Integer i = InputReader.readNumber(val);
        // then
        assertEquals(1, i);
    }

    @Test
    void test2() {
        // given
        String s = "a";
        // when
        Integer i = InputReader.readNumber(s);
        // then
        assertNull(i);
    }


    static Stream<Arguments> validCoordinateProvider() {
        return Stream.of(
            Arguments.of("0 0", new Coordinate(0, 0)),
            Arguments.of("2 2", new Coordinate(2, 2))
        );
    }

    @ParameterizedTest
    @MethodSource("validCoordinateProvider")
    void testReadCoordinate_ValidInput(String input, Coordinate expectedCoordinate) {
        // given
        Maze maze = createSampleMaze();
        // when
        Coordinate result = InputReader.readCoordinate(maze, input);
        // then
        assertEquals(expectedCoordinate, result);
    }

    static Stream<Arguments> invalidCoordinateProvider() {
        return Stream.of(
            Arguments.of("3 3", OUT_OF_MAZE_ERROR),
            Arguments.of("-1 0", OUT_OF_MAZE_ERROR),
            Arguments.of("1 1", IS_A_WALL_ERROR)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidCoordinateProvider")
    void testReadCoordinate_InvalidCoordinate(String input, String errorMessage) {
        // given
        Maze maze = createSampleMaze();
        // when - then
        assertThatThrownBy(() -> {
            InputReader.readCoordinate(maze, input);
        }).isInstanceOf(InputReadException.class)
            .hasMessageContaining(errorMessage);
    }

    static Stream<String> invalidInputFormatProvider() {
        return Stream.of(
            "invalid input",
            "1",
            "1 invalid"
        );
    }

    @ParameterizedTest
    @MethodSource("invalidInputFormatProvider")
    void testReadCoordinate_InvalidInputFormat(String input) {
        Maze maze = createSampleMaze();
        assertThatThrownBy(() -> {
            InputReader.readCoordinate(maze, input);
        }).isInstanceOf(InputReadException.class)
            .hasMessageContaining(INVALID_INPUT_ERROR);
    }

}
