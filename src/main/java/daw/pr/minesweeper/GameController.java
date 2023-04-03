package daw.pr.minesweeper;

import daw.pr.minesweeper.struct.Difficulty;
import daw.pr.minesweeper.struct.Game;
import daw.pr.minesweeper.struct.StateCanvas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
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
        StringBuilder tableHtml = new StringBuilder();
        tableHtml.append("<table>");
        for (int i = 0; i < game.getRows(); i++) {
            tableHtml.append("<tr>");
            for (int j = 0; j < game.getColumns(); j++) {
                if (game.getCell(i, j).getStateCanvas() == StateCanvas.REVEALED) {
                    tableHtml.append("<td>");
                    if (game.getCell(i, j).getStateSelf() == daw.pr.minesweeper.struct.StateSelf.MINE) {
                        tableHtml.append("X");
                    } else {
                        tableHtml.append(game.getCell(i, j).getMinesAround());
                    }
                    tableHtml.append("</td>");
                    continue;
                } else {
                    tableHtml.append("<td>");
                    tableHtml.append("<form action='/revealCell' method='post'>");
                    tableHtml.append("<input type='hidden' name='row' value='" + i + "'/>");
                    tableHtml.append("<input type='hidden' name='column' value='" + j + "'/>");
                    tableHtml.append("<button type='submit'></button>");
                    tableHtml.append("</form>");
                    tableHtml.append("</td>");
                }
            }
        }
        return tableHtml.toString();
    }

    @PostMapping("/revealCell")
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
