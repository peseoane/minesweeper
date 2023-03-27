package daw.pr.minesweeper.struct;

public class Cell {

    private State state;
    private Difficulty difficulty;

    /**
     * Crea una celda
     *
     * @param int Difficulty de 0 a 3
     */
    public Cell(Difficulty difficulty) {
        this.state = State.HIDDEN;
        this.difficulty = difficulty;
    }

}
