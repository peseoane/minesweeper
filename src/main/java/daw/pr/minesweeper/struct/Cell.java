package daw.pr.minesweeper.struct;

public class Cell {

    private State state;

    public Cell(Difficulty difficulty) {
        this.state = State.HIDDEN;
    }

}
