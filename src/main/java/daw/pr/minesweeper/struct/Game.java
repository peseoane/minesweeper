package daw.pr.minesweeper.struct;

import java.util.Arrays;

public class Game {

    private final Cell[][] cells;
    private final Difficulty difficulty;

    public Game(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.cells = new Cell[difficulty.getRows()][difficulty.getColumns()];

        fillHidden();
        fillMines(difficulty);
    }

    private void fillMines(Difficulty difficulty) {
        for (int i = 0; i < difficulty.getMines(); i++) {
            int row = (int) (Math.random() * difficulty.getRows());
            int column = (int) (Math.random() * difficulty.getColumns());

            if (cells[row][column].getStateSelf() == StateSelf.MINE) {
                i--;
            } else {
                cells[row][column] = new Cell(StateSelf.MINE);
            }
        }
    }

    private void fillHidden() {
        for (Cell[] row : cells) {
            Arrays.fill(row, new Cell());
        }
    }
}
