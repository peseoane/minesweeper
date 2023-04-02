package daw.pr.minesweeper;

import daw.pr.minesweeper.struct.Difficulty;
import daw.pr.minesweeper.struct.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class App {

    private final static Logger LOGGER = LoggerFactory.getLogger(App.class);

    private static void logSystemInfo() {
        LOGGER.info("Java System Properties:");
        System.getProperties().forEach((k, v) -> LOGGER.info(k + " = " + v));
        LOGGER.info("End of Java System Properties");
    }

    public static void main(String[] args) {
        logSystemInfo();
        LOGGER.info("Starting Minesweeper");

        SpringApplication.run(App.class, args);
        Game game = new Game(Difficulty.EASY);

    }

    @RestController("/")
    class HelloWorld {
        @GetMapping("hello")
        public String hello() {
            return "Hi, Bela!";
        }
    }

}
