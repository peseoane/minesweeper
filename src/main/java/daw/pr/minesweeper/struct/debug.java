package daw.pr.minesweeper.struct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface debug {

    public final static Logger LOGGER = LoggerFactory.getLogger(debug.class);

    @Override
    String toString();

}
