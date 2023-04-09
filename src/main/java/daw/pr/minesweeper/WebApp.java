package daw.pr.minesweeper;

import daw.pr.minesweeper.struct.Difficulty;
import daw.pr.minesweeper.struct.Game;
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

    @GetMapping("/debug")
    @ResponseBody
    public String debug() {
        String debug = getTableHtml() + "<div class='panel'>" + game.toString() + "</div>";
        return debug;
    }

    @GetMapping("/getTableHtml")
    @ResponseBody
    public String getTableHtml() {
        StringBuilder html = new StringBuilder();
        // add debug get button

        html.append(applyCSSproduction(html));
        html.append("<div class='container'>");

        html.append(createMineBoard());


        if (game.isGameOver()) {
            html.append("<form action='/gameOver.html' method='get'>");
            html.append("<input type='submit' value='Game Over'>");
            html.append("</form>");
        } else if (game.isWin()) {
            html.append("<form action='/win.html' method='get'>");
            html.append("<input type='submit' value='You Win'>");
            html.append("</form>");
        }

        if (game.isGameOver() || game.isWin()) {
            game.saveWinner();
            html.append("<table class='hallFame'>");
            html.append("<tr><th>ID</th><th>name</th><th>Date</th><th>score</th></tr>");
            for (String[] row : game.getHallFame()) {
                String id = row[0];
                String name = row[1];
                String date = row[2];
                String score = row[3];

                html.append("<tr><td>")
                    .append(id)
                    .append("</td><td>")
                    .append(name)
                    .append("</td><td>")
                    .append(date)
                    .append("</td><td>")
                    .append(score)
                    .append("</td></tr>");
            }

            html.append("</table>");
        }

        html.append("<form action='/debug' method='get'>");
        html.append("<input class='debug' type='submit' value='\uD83D\uDEE0\uFE0F Debug panel'>");
        html.append("</form>");
        html.append("</div>");

        return html.toString();
    }

    private StringBuilder applyCSSproduction(StringBuilder html) {
        html.append("<link rel='stylesheet' href='./style.css'>");
        return html;
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

    private StringBuilder createMineBoard() {
        StringBuilder mineBoard = new StringBuilder();
        mineBoard.append("<div class='game'>");
        for (int i = 0; i < game.getRows(); i++) {
            mineBoard = createParentDiv(mineBoard, "row", createRow(i));
        }
        mineBoard.append("</div>");
        return mineBoard;
    }

    private StringBuilder createParentDiv(StringBuilder baseHtml, String divClass, StringBuilder insideDiv) {
        baseHtml.append("<div class='").append(divClass).append("'>").append(insideDiv).append("</div>");
        return baseHtml;
    }

    private StringBuilder createRow(int row) {
        StringBuilder rowHtml = new StringBuilder();
        for (int i = 0; i < game.getColumns(); i++) {
            rowHtml.append(createCell(row, i));
        }
        return rowHtml;
    }

    private StringBuilder createCell(int row, int column) {
        StringBuilder cell = new StringBuilder();
        cell.append("<a href='/revealCell?row=")
            .append(row)
            .append("&column=")
            .append(column)
            .append("'>")
            .append("<div class='cell'>");

        if (! game.isHidden(row, column)) {
            if (game.isMine(row, column)) {
                final String mine = ".assets/bomb.webp";
                cell.append("<img src='" + mine + "' alt='mine'>");
            } else {
                cell.append(game.getCell(row, column).getMinesAround());
            }
        }

        cell.append("</div>").append("</a>");
        return cell;
    }

}
