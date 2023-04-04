package daw.pr.minesweeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private static void logSystemInfo() {
        LOGGER.info("Java System Properties:");
        System.getProperties().forEach((k, v) -> LOGGER.info(k + " = " + v));
        LOGGER.info("End of Java System Properties");
    }

    public static void main(String[] args) {
        logSystemInfo();
        LOGGER.info("Starting Minesweeper");
        SpringApplication.run(App.class, args);
    }
}
