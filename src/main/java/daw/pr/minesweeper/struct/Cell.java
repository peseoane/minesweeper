package daw.pr.minesweeper.struct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cell implements debug {

    private static final Logger logger = LogManager.getLogger(Cell.class);
    private int offset = 0;
    private int[] position;
    private StateCanvas stateCanvas;
    private StateSelf stateSelf;

    public Cell() {
        this.stateCanvas = StateCanvas.HIDDEN;
        this.stateSelf = StateSelf.CLEAR;
    }

    public Cell(StateSelf stateSelf) {
        this.stateCanvas = StateCanvas.HIDDEN;
        this.stateSelf = stateSelf;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

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

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int getRow() {
        return position[0];
    }

    public int getColumn() {
        return position[1];
    }

    @Override
    public String toString() {
        return "Cell{" + "stateCanvas=" + stateCanvas + ", stateSelf=" + stateSelf + '}';
    }

}
