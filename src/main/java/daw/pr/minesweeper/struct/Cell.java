package daw.pr.minesweeper.struct;

public class Cell implements debug {

    private int offset = 0;
    private int[] position;
    private StateCanvas stateCanvas;
    private StateSelf stateSelf;
    private int minesAround = 0;

    public Cell() {
        this.stateCanvas = StateCanvas.HIDDEN;
        this.stateSelf = StateSelf.CLEAR;
    }

    public Cell(StateSelf stateSelf) {
        this.stateCanvas = StateCanvas.HIDDEN;
        this.stateSelf = stateSelf;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
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

// --Commented out by Inspection START (04/04/2023 11:34):
//    public void uncoverCell(Cell cell) {
//        cell.setStateCanvas(StateCanvas.REVEALED);
//        LOGGER.debug("Cell: " + cell);
//    }
// --Commented out by Inspection STOP (04/04/2023 11:34)

// --Commented out by Inspection START (04/04/2023 11:33):
//    public int[] getPosition() {
//        return position;
//    }
// --Commented out by Inspection STOP (04/04/2023 11:33)

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
