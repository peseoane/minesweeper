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

@SuppressWarnings("SpringMVCViewInspection")
@Controller
public class WebApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebApp.class);
    Game game;

    @SuppressWarnings({"SpringMVCViewInspection", "SameReturnValue"})
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping("/startGame")
    @ResponseBody
    public String startGame(@RequestParam("difficulty") String difficultyString, @RequestParam("name") String name) {
        Difficulty difficulty = Difficulty.valueOf(difficultyString.toUpperCase());
        game = new Game(difficulty, name);
        LOGGER.info("Starting game with difficulty: " + difficulty);
        return getTableHtml();
    }

    // generate ERROR page
    @SuppressWarnings({"SpringMVCViewInspection", "SameReturnValue"})
    @GetMapping("/error")
    public String error() {
        LOGGER.error("El objeto game no ha sido inicializado");

        return "index.html";
    }

    @GetMapping("/getScoreValue")
    @ResponseBody
    public int getScoreValue() {
        return game.getScoreValue();
    }

    @GetMapping("/getTableHtml")
    @ResponseBody
    public String getTableHtml() {
        final String ASSET_MINE = "assets/bomb.webp";
        StringBuilder html = new StringBuilder();

        html.append("<link rel='stylesheet' href='./style.css'>");
        html.append("<div class='container'>");
        html.append("<div class='score'>");
        html.append("<div id='score' class='score'>Score: ").append(game.getScoreValue()).append("</div>");
        html.append("</div>");
        html.append("<table>");

        for (int i = 0; i < game.getRows(); i++) {
            html.append("<tr>");
            for (int j = 0; j < game.getColumns(); j++) {
                html.append("<td>");

                html.append("<div class='cell'>");
                html.append("<a href='/revealCell?row=").append(i).append("&column=").append(j).append("'>");
                if (
                        game.getCell(i, j).getStateCanvas() == StateCanvas.REVEALED &&
                                game.getCell(i, j).getStateSelf() == StateSelf.MINE
                ) {
                    html.append("<img class='mine' src='" + ASSET_MINE + "'>");
                } else if (game.getCell(i, j).getStateCanvas() == StateCanvas.REVEALED) {
                    html.append("<p class='number'>").append(game.getCell(i, j).getMinesAround()).append("</p>");
                } else {
                    html.append("<p>?</p>");
                }

                html.append("</a>");
                html.append("</td>");
            }
            html.append("</tr>");
        }
        html.append("</table>");

        if (game.isGameOver()) {
            html.append("<form action='/gameOver.html' method='get'>");
            html.append("<input type='submit' value='Game Over'>");
            html.append("</form>");
        } else if (game.isWin()) {
            html.append("<form action='/win.html' method='get'>");
            html.append("<input type='submit' value='You Win'>");
            html.append("</form>");
        }

        html.append("</div>");

        return html.toString();
    }

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/revealCell")
    public String revealCell(@RequestParam int row, @RequestParam int column) {
        LOGGER.info("Clicked on Row: " + row + " Column: " + column);
        game.uncoverClickedCell(row, column);
        LOGGER.info("Cell: " + game.getCell(row, column));
        LOGGER.info("Game: " + game);
        LOGGER.info("REVEALED");
        return "redirect:/getTableHtml";
    }

    @SuppressWarnings({"SpringMVCViewInspection", "SameReturnValue"})
    @PostMapping("/gameOver")
    public String gameOver() {
        return "gameOver.html";
    }
}
