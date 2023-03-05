package daw.pr.minesweeper.struct;

public class Cell {

    private boolean hasBeenRevealed;
    private boolean hasBeenFlagged;
    private boolean hasBeenMarked;

    /**
     * Crea una celda
     * @param int difficulty de 0 a 3
     */
    public Cell(int difficulty) {
        this.hasBeenRevealed = false;
        this.hasBeenFlagged = false;
        this.hasBeenMarked = false;
    }

}
