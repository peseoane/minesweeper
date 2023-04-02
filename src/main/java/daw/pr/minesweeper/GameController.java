package daw.pr.minesweeper;

import daw.pr.minesweeper.struct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameController {

    private int moves = 0;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    Game game;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping("/startGame")
    @ResponseBody
    public String startGame(@RequestParam("difficulty") String difficultyString) {
        Difficulty difficulty = Difficulty.valueOf(difficultyString.toUpperCase());
        game = new Game(difficulty);
        LOGGER.info("Starting game with difficulty: " + difficulty);
        return getTableHtml();
    }

    @GetMapping("/revealCell")
    public String revealCell(@RequestParam("row") int row, @RequestParam("column") int column) {
        LOGGER.info("Revealing cell: " + row + ", " + column);
        if (game == null) {
            LOGGER.error("Game object is null!");
        }
        LOGGER.info("Game object is not null");
        game.getCell(row, column).setStateCanvas(StateCanvas.REVEALED);
        // crear y devolver la tabla actualizada
        return getTableHtml();
    }

    @GetMapping("/getTableHtml")
    public String getTableHtml() {

        int totalCells = game.getTotalCells();
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Minesweeper</h1>");
        sb.append("moves: " + moves);
        moves++;
        sb.append("<link rel='stylesheet' href='./style.css'>");
        sb.append("<script src='./app.js'></script>");
        sb.append("<div id='table-container'>");
        sb.append("<table>");
        for (Cell[] row : game.getCells()) {
            sb.append("<tr>");

            for (Cell cell : row) {
                sb.append("<td>");
                if (cell.getStateSelf() == StateSelf.MINE) {
                    sb.append("<div class='mine'>.</div> ");
                } else if (cell.getStateCanvas() == StateCanvas.HIDDEN) {
                    sb.append("<div class='hidden' onclick='revealCell(" + cell.getRow() + ", " + cell.getColumn() +
                                      ")'>_</div> ");
                } else {
                    sb.append("<div class='visible'>" + cell.getMinesAround() + "</div> ");
                }
                sb.append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("</div>");
        sb.append("<p>Remaining cells:</p>");
        sb.append("<p id='remaining-cells'>" + game.getTotalCells() + "</p>");
        sb.append("<button onclick='window.location.reload()'>Refresh</button>");

        for (Cell[] row : game.getCells()) {
            for (Cell cell : row) {
                if (cell.getStateSelf() == StateSelf.MINE) {
                    sb.append("* ");
                } else {
                    sb.append(cell.getMinesAround() + " ");
                }
            }
            System.out.println();
        }

        sb.append(game.getCells());
        return sb.toString();
    }
}
