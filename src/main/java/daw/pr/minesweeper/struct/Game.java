package daw.pr.minesweeper.struct;

public class Game {

    private Cell cells[][];
    private Difficulty difficulty;

    public Game(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.cells = new Cell[difficulty.getRows()][difficulty.getColumns()];
        for (int i = 0; i < difficulty.getRows(); i++) {
            for (int j = 0; j < difficulty.getColumns(); j++) {
                this.cells[i][j] = new Cell(difficulty);
            }
        }
    }

}
