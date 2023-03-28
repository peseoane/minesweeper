package daw.pr.minesweeper.struct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Game implements debug {

    private static final Logger logger = LogManager.getLogger(Game.class);

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

            int remeaningMines = difficulty.getMines();

            for (int row = 0; row < difficulty.getRows(); row++) {
                for (int column = 0; column < difficulty.getColumns(); column++) {
                    if (remeaningMines > 0 && Math.random() * 100 < 5) {
                        cells[row][column].setStateSelf(StateSelf.MINE);
                        remeaningMines--;
                    }
                }
            }
        }

    }

    private void fillHidden() {
        for (Cell[] row : cells) {
            Arrays.fill(row, new Cell());
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Game{").append("cells=\n");

        for (Cell[] row : cells) {
            sb.append("    ");
            for (Cell cell : row) {
                sb.append(cell.getStateSelf()).append(" ");
            }
            sb.append("\n");
        }
        sb.append("    }").append(", difficulty=").append(difficulty).append('}');
        return sb.toString();

    }

}
