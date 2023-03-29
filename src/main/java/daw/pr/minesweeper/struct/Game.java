package daw.pr.minesweeper.struct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Game implements debug {

    private static final Logger logger = LogManager.getLogger(Game.class);
    private final Difficulty difficulty;
    private final Cell[][] cells;

    public Game(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.cells = new Cell[difficulty.getRows()][difficulty.getColumns()];
        fillHidden();
        fillMines(difficulty);
    }

    private void fillMines(Difficulty difficulty) {
        int contador = 0;
        int remainingMines = difficulty.getMines();

        while (remainingMines > 0) {
            for (int i = 0; i < difficulty.getColumns(); i++) {
                for (int j = 0; j < difficulty.getRows(); j++) {
                    if (remainingMines > 0 && Math.random() < 0.1) {
                        cells[i][j].setStateSelf(StateSelf.MINE);
                        remainingMines--;
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("Cell: " + contador + " - " + cells[i][j]);
                        contador++;
                    }
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

