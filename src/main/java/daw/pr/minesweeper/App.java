package daw.pr.minesweeper;

import daw.pr.minesweeper.struct.Difficulty;
import daw.pr.minesweeper.struct.Game;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    private static void logSystemInfo() {
        logger.info("Java System Properties:");
        System.getProperties().forEach((k, v) -> logger.info(k + " = " + v));
        logger.info("End of Java System Properties");
    }

    public static void main(String[] args) {
        // Check if the String[] args contains the debug flag (--debug or -d)
        // if other than --debug -d or nothing is display, raise and exception
        // and exit the program
        if (args.length > 0) {
            if (args[0].equals("--debug") || args[0].equals("-d")) {
                // change root logger for log4j2 to DEBUG
                Configurator.setRootLevel(Level.DEBUG);
                logger.info("Debug mode enabled");
            } else {
                logger.error("Invalid argument: " + args[0]);
                logger.error("Usage: java -jar minesweeper.jar [--debug|-d]");
                System.exit(1);
            }
        } else {
            Configurator.setRootLevel(Level.ERROR);
        }

        logger.info("Starting Minesweeper");

        logSystemInfo();

        Game game = new Game(Difficulty.EASY);

        game.getAdjacentCells(game.getCell(0, 0));

        System.out.println(game);


    }
}
