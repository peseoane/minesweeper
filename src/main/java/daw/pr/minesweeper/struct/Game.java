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
        int remainingMines = difficulty.getMines();
        while (remainingMines > 0) {
            for (Cell[] row : cells) {
                for (Cell cell : row) {

                    if (remainingMines > 0) {
                        cell.setStateSelf(StateSelf.MINE);
                        remainingMines--;
                        if (remainingMines == 0) {
                            break; // salir del bucle for
                        }
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
