package daw.pr.minesweeper.struct;

public enum Difficulty {

    EASY(6, 6, 6),
    MEDIUM(16, 16, 40),
    HARD(16, 30, 99);
    // CUSTOM(0, 0, 0);

    private int rows;
    private int columns;
    private int mines;

    private Difficulty(int rows, int columns, int mines) {
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