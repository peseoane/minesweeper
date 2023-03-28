package daw.pr.minesweeper.struct;

public class Cell implements Gameplay {

    private State state;

    public Cell() {
        this.state = State.HIDDEN;
    }

    public Cell(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
