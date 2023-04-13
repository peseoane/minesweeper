package daw.pr.minesweeper.struct;

public enum Difficulty {
    EASY(6, 6, 6),
    MEDIUM(10, 10, 10),
    HARD(14, 14, 14);

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
