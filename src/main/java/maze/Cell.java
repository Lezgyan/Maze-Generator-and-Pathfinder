package maze;

import lombok.Getter;

@Getter
public enum Cell {

    WALL("\u2B1C"),

    ROAD("\u2B1B"),

    SHORT_PATH("\uD83D\uDFE9"),

    START("\uD83D\uDFE8"),

    FINISH("\uD83D\uDFE5"),

    ROAD_GOOD("\uD83D\uDC8E"),

    ROAD_BAD("\uD83C\uDFD4\uFE0F");


    private final String color;

    Cell(String c) {
        this.color = c;
    }
}
