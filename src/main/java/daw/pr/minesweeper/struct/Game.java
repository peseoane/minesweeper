package daw.pr.minesweeper.struct;

import daw.pr.minesweeper.SQL.SQLDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Game implements debug, gameplay {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private final Difficulty difficulty;
    private final Cell[][] cells;
    private final long initTime = System.currentTimeMillis();
    private final SQLDriver sql = new SQLDriver();
    private boolean gameOver = false;
    private String playerName;
    private int points, moves;
    private int scoreValue;

    public Game(Difficulty difficulty, String playerName) {
        this.difficulty = difficulty;
        this.cells = new Cell[difficulty.getRows()][difficulty.getColumns()];
        generateCanvas(difficulty);
        printGame();
        this.playerName = playerName;
    }

    public int getScoreValue() {
        calculateScoreValue();
        return scoreValue;
    }

    private void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    private int minesFound() {
        int minesFound = 0;
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getStateSelf() == StateSelf.MINE && cell.getStateCanvas() == StateCanvas.REVEALED) {
                    minesFound++;
                }
            }
        }
        return minesFound;
    }

    private void calculateScoreValue() {
        long elapsedTime = (System.currentTimeMillis() - initTime) / 1000;
        setScoreValue((int) elapsedTime);
    }

    public int getRows() {
        return difficulty.getRows();
    }

    public int getColumns() {
        return difficulty.getColumns();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void saveWinner() {
        // print all from the sqlite select ALL
        System.out.println(sql.ejecutarQuery("SELECT * FROM scores"));
        sql.ejecutarActualizacion("INSERT INTO scores (name, score) VALUES ('" + playerName + "', " + scoreValue + ")");
    }

    public ArrayList<String[]> getHallFame() {
        return sql.ejecutarQuery("SELECT * FROM scores ORDER BY score DESC LIMIT 10");
    }

    private void generateCanvas(Difficulty difficulty) {
        fillHidden();
        fillMines(difficulty);
        calculateNumbers();
    }

    private void printGame() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getStateSelf() == StateSelf.MINE) {
                    System.out.print("* ");
                } else {
                    System.out.print(cell.getMinesAround() + " ");
                }
            }
            System.out.println();
        }
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
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Cell: " + contador + " - " + cells[i][j]);
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
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Cell: " + i + " - " + j + " - " + cells[i][j]);
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
                if (cell.getStateSelf() == StateSelf.MINE) {
                    sb.append("*");
                } else {
                    sb.append(cell.getMinesAround());
                }
            }
            sb.append(System.getProperty("line.separator"));
        }
        sb
                .append("    }")
                .append("\n, difficulty=")
                .append(difficulty)
                .append('}')
                .append(gameOver ? " Game Over " : " Game not over ")
                .append(" Playername: ")
                .append(playerName)
                .append(" Score: ")
                .append(scoreValue)
                .append(" Moves: " + moves)
                .append(" Time: " + (System.currentTimeMillis() - initTime) / 1000);
        return sb.toString();
    }

    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

    /**
     * This function uses an array of offsets accessible through an index contained in the offset object. This allows
     * to recursively query in each iteration the adjacent cells and without using an argument to move to the next
     * iteration.
     *
     * @param cell the cell to query the neighbors cells
     * @return all valid adjacent cells
     */
    public ArrayList<Cell> getAdjacentCells(Cell cell) {
        ArrayList<Cell> adjacentCells = new ArrayList<>();

        // This could be done dynamically, but I'm lazy... also it's faster, at the end the compiler will do the
        // same thing
        final int[][] offsets = {
                {- 1, - 1},
                {- 1, 0},
                {- 1, 1},
                {0, - 1},
                {0, 1},
                {1, - 1},
                {1, 0},
                {1, 1}
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
                LOGGER.debug("Cell: " + x + " - " + y + " - " + getCell(x, y));
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
        // this is not particularly safe, but it's faster
        return x >= 0 && x < difficulty.getRows() && y >= 0 && y < difficulty.getColumns();
    }

    public ArrayList<Cell> getAdjacentMines(Cell cell) {
        ArrayList<Cell> adjacentMines = new ArrayList<>();

        // This could be done dynamically, but I'm lazy, and also it's faster, at the end the compiler will do the
        // same thing
        final int[][] offsets = {
                {- 1, - 1},
                {- 1, 0},
                {- 1, 1},
                {0, - 1},
                {0, 1},
                {1, - 1},
                {1, 0},
                {1, 1}
        };

        if (cell.getOffset() < offsets.length) {
            int x_offset = offsets[cell.getOffset()][0];
            int y_offset = offsets[cell.getOffset()][1];

            int x = cell.getRow() + x_offset;
            int y = cell.getColumn() + y_offset;

            // Check if the adjacent cell is a valid cell
            // This code is UNSAFE due to a possible null pointer exception

            try {
                if (isValidCell(x, y) && getCell(x, y).getStateSelf() == StateSelf.MINE) {
                    // Add the adjacent cell to the list of adjacent cells
                    adjacentMines.add(getCell(x, y));
                    LOGGER.debug(
                            "Cell: " + x + " - " + y + " - " + getCell(x, y) + " - " + getCell(x, y).getStateSelf()
                                );
                }
            } catch (Exception IndexOutOfBoundsException) {
                LOGGER.error("Error: " + IndexOutOfBoundsException.getMessage());
            }

            // Next query will be for the next position of the offset
            cell.setOffset(cell.getOffset() + 1);

            // Recursively call
            adjacentMines.addAll(getAdjacentMines(cell));
        } else {
            // Reset the offset... this is ugly, but it works
            cell.setOffset(0);
        }

        // Return the list of adjacent cells found so far
        return adjacentMines;
    }

    public void uncoverCell(Cell cell) {
        cell.setStateCanvas(StateCanvas.REVEALED);
        LOGGER.debug(
                "Cell: " +
                        cell.getRow() +
                        " - " +
                        cell.getColumn() +
                        " - " +
                        cell.getStateSelf() +
                        " - " +
                        cell.getStateCanvas()
                    );
    }

    public void uncoverAllCells() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.setStateCanvas(StateCanvas.REVEALED);
            }
        }
        LOGGER.debug("Cells revealed");
    }

    public void uncoverClickedCell(Cell cell) {
        if (moves == 0 && cell.getStateSelf() == StateSelf.MINE) {
            firstMoveCannotBeLoose(cell);
            uncoverClickedCell(cell);
        } else if (cell.getStateSelf() == StateSelf.MINE) {
            uncoverAllCells();
            setGameOver(true);
        } else {
            uncoverCell(cell);
            if (cell.getMinesAround() == 0) {
                for (Cell adjacentCell : getAdjacentCells(cell)) {
                    if (adjacentCell.getStateCanvas() == StateCanvas.HIDDEN) {
                        uncoverClickedCell(adjacentCell);
                    }
                }
            }
        }
        moves++;
    }

    public boolean isWin() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                // check that all the cells that are not mines are revealed
                if (cell.getStateSelf() != StateSelf.MINE && cell.getStateCanvas() != StateCanvas.REVEALED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void firstMoveCannotBeLoose(Cell cell) {
        // If the first move is a mine, move the mine to another cell
        cell.setStateSelf(StateSelf.CLEAR);
        // Get a random cell

        // If the random cell is not a mine, set it as a mine
        if (
                getCell((int) (Math.random() * difficulty.getRows()), (int) (Math.random() * difficulty.getColumns()))
                        .getStateSelf() !=
                        StateSelf.MINE
        ) {
            getCell((int) (Math.random() * difficulty.getRows()), (int) (Math.random() * difficulty.getColumns()))
                    .setStateSelf(StateSelf.MINE);
        }
    }

    public void uncoverClickedCell(int row, int column) {
        if (moves == 0 && getCell(row, column).getStateSelf() == StateSelf.MINE) {
            firstMoveCannotBeLoose(getCell(row, column));
        } else if (getCell(row, column).getStateSelf() == StateSelf.MINE) {
            uncoverAllCells();
            setGameOver(true);
            LOGGER.debug("Game over");
        } else {
            uncoverCell(getCell(row, column));
            if (getCell(row, column).getMinesAround() == 0) {
                for (Cell adjacentCell : getAdjacentCells(getCell(row, column))) {
                    if (adjacentCell.getStateCanvas() == StateCanvas.HIDDEN) {
                        uncoverClickedCell(adjacentCell);
                    }
                }
            }
        }
        moves++;
    }

    public boolean isMine(int row, int column) {
        return getCell(row, column).getStateSelf().equals(StateSelf.MINE) ? true : false;
    }

    public boolean isHidden(int row, int column) {
        return getCell(row, column).getStateCanvas().equals(StateCanvas.HIDDEN) ? true : false;
    }

    public void calculateNumbers() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                // Count the number of adjacent mines
                cell.setMinesAround(getAdjacentMines(cell).size());
                LOGGER.info("Number of mines around: " + cell.getMinesAround());
            }
        }
    }

    /* Obsolete code, but I'm too lazy to delete it in case will need it again and
    if future use of javascript + listeners instead of java + interfaces
    */
    @Override
    public void rightClick(Cell cell) {
        if (cell.getStateCanvas() == StateCanvas.REVEALED) {
            return;
        }
        if (cell.getStateCanvas() == StateCanvas.FLAGGED) {
            cell.setStateCanvas(StateCanvas.QUESTIONED);
        } else if (cell.getStateCanvas() == StateCanvas.QUESTIONED) {
            cell.setStateCanvas(StateCanvas.HIDDEN);
        } else {
            cell.setStateCanvas(StateCanvas.FLAGGED);
        }
    }

    @Override
    public void leftClick(Cell cell) {
        if (cell.getStateCanvas() == StateCanvas.FLAGGED) {
            return;
        }
        uncoverClickedCell(cell);
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }
    // --Commented out by Inspection START (04/04/2023 11:34):
    //    public Cell[][] getCells() {
    //        return cells;
    //    }
    // --Commented out by Inspection STOP (04/04/2023 11:34)
}
