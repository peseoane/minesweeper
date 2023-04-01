package daw.pr.minesweeper.struct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

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
                cells[i][j].setPosition(new int[]{i, j});
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

    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

    public ArrayList<Cell> getAdjacentCells(Cell cell) {
        ArrayList<Cell> adjacentCells = new ArrayList<>();

        // This could be done dinamically, but I'm lazy and also it's faster, at the end the compiler will do the
        // same thing
        final int[][] offsets = {
                {- 1, - 1}, {- 1, 0}, {- 1, 1},
                {0, - 1}, {0, 1},
                {1, - 1}, {1, 0}, {1, 1}
        };

        if (cell.getOffset() < offsets.length) {

            int x_offset = offsets[cell.getOffset()][0];
            int y_offset = offsets[cell.getOffset()][1];

            int x = cell.getRow() + x_offset;
            int y = cell.getColumn() + y_offset;

            // Check if the adjacent cell is a valid cell
            if (isValidCell(x, y)) {
                // Add the adjacent cell to the list of adjacent cells
                adjacentCells.add(getCell(x, y));
                logger.debug("Cell: " + x + " - " + y + " - " + getCell(x, y));
            }

            // Next query will be for the next position of the offset
            cell.setOffset(cell.getOffset() + 1);

            // Recursively call
            adjacentCells.addAll(getAdjacentCells(cell));
        } else {
            // Reset the offset... this is ugly, but it works
            cell.setOffset(0);
        }

        // Return the list of adjacent cells found so far
        return adjacentCells;
    }


    private boolean isValidCell(int x, int y) {
        // verifica si la celda está dentro del tablero
        return x >= 0 && x < difficulty.getRows() && y >= 0 && y < difficulty.getColumns();
    }

}
