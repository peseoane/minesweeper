package daw.pr.minesweeper.struct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        int contador = 0;
        int remainingMines = difficulty.getMines();
        for (Cell[] row : cells) {
            for (Cell cell : row) {

                if (remainingMines > 0) {
                    cell.setStateSelf(StateSelf.MINE);
                    remainingMines--;
                } else {
                    break;
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("Cell: " + contador + " - " + cell);
                    contador++;
                }
            }
        }
    }

    private void fillHidden() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell();
                if (logger.isDebugEnabled()) {
                    logger.debug("Cell: " + i + " - " + j + " - " + cells[i][j]);
                }
            }
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Game{").append("cells=\n");

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                sb.append(" ").append(cell.getStateSelf());
            }
            sb.append("\n");
        }
        sb.append("    }").append(", difficulty=").append(difficulty).append('}');
        return sb.toString();
    }
}

