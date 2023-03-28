package daw.pr.minesweeper.struct;

public enum Difficulty {

    EASY(6, 6, 4),
    MEDIUM(16, 16, 40),
    HARD(16, 30, 99);
    // CUSTOM(0, 0, 0);

    private final int rows;
    private final int columns;
    private final int mines;

    Difficulty(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getMines() {
        return mines;
    }

}
