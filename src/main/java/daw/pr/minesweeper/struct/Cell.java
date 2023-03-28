package daw.pr.minesweeper.struct;

public class Cell implements Gameplay {

    private StateCanvas stateCanvas;

    public StateCanvas getStateCanvas() {
        return stateCanvas;
    }

    public void setStateCanvas(StateCanvas stateCanvas) {
        this.stateCanvas = stateCanvas;
    }

    public StateSelf getStateSelf() {
        return stateSelf;
    }

    public void setStateSelf(StateSelf stateSelf) {
        this.stateSelf = stateSelf;
    }

    private StateSelf stateSelf;

    public Cell() {
        this.stateCanvas = StateCanvas.HIDDEN;
        this.stateSelf = StateSelf.CLEAR;
    }

    public Cell(StateSelf stateSelf) {
        this.stateSelf = stateSelf;
    }


}
