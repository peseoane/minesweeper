package daw.pr.minesweeper.struct;

public class Cell {

    private State state;
    private Difficulty difficulty;


    public Cell(Difficulty difficulty) {
        this.state = State.HIDDEN;
        this.difficulty = difficulty;
    }

}
