package daw.pr.minesweeper;

import daw.pr.minesweeper.struct.Difficulty;
import daw.pr.minesweeper.struct.Game;
import daw.pr.minesweeper.struct.StateCanvas;
import daw.pr.minesweeper.struct.StateSelf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebApp.class);
    private final int moves = 0;
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

    @GetMapping("/getTableHtml")
    @ResponseBody
    public String getTableHtml() {
        StringBuilder html = new StringBuilder();
        html.append("<link rel='stylesheet' href='./style.css'>");
        String image = "./assets/mosaicoAmarillo.svg";
        html.append("<div id='container'>");
        html.append("<table>");
        for (int i = 0; i < game.getRows(); i++) {
            html.append("<tr>");
            for (int j = 0; j < game.getColumns(); j++) {

                html.append("<td>");
                html.append("<div class='cell'");

                if (game.getCell(i, j).getStateCanvas() == StateCanvas.REVEALED && game.getCell(i, j).getStateSelf() == StateSelf.MINE) {
                    html.append("div class='cellInside'");
                    html.append("<img class='mine' src='./assets/bomb.svg'>");
                    html.append("</div>");
                } else if (game.getCell(i, j).getStateCanvas() == StateCanvas.REVEALED) {
                    html.append("div class='cellInside'");
                    html.append("<p class='number'>" + game.getCell(i, j).getMinesAround() + "</p>");
                    html.append("</div>");
                }

                html.append("<div class=bg>");
                html.append("<a href='/revealCell?row=" + i + "&column=" + j + "'>");
                html.append("<img class='unknownCell' src= " + image + " style='display: block; width: 100%; height: " +
                                    "100%;' >");
                html.append("</div>");
                html.append("</td>");
                html.append("</div>");
                html.append("</td>");
            }
            html.append("</tr>");
        }
        html.append("</table>");
        html.append("</div>");

        return html.toString();
    }

    @GetMapping("/revealCell")
    public String revealCell(@RequestParam int row, @RequestParam int column) {
        LOGGER.info("Clicked on Row: " + row + " Column: " + column);
        game.uncoverClickedCell(row, column);
        LOGGER.info("Cell: " + game.getCell(row, column));
        LOGGER.info("Game: " + game);
        LOGGER.info("REVEALED");
        if (game.isGameOver()) {
            LOGGER.info("GAME OVER");
            return "redirect:/gameOver";
        } else {
            return "redirect:/getTableHtml";
        }
    }

    @PostMapping("/gameOver")
    public String gameOver() {
        return "gameOver.html";
    }

}
