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
    Boolean showDebug = false;

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
        return mainHTMLgen();
    }

    // generate ERROR page
    @SuppressWarnings({"SpringMVCViewInspection", "SameReturnValue"})
    @GetMapping("/error")
    public String error() {
        LOGGER.error("El objeto game no ha sido inicializado");
        return "index.html";
    }

    private StringBuilder credits() {
        StringBuilder html = new StringBuilder();
        html.append(
                """
                            <div class="credits">
                                <p class="profile">Created by <a href="github.com/peseoane">Pedro V. Seoane Prado</a></p>
                                <p class="repo">
                                    Check out the source code at
                                    <a href="github.com/peseoane/minesweeper">peseoane /minesweeper</a>
                                </p>
                            </div>
                        """
                   );
        return html;
    }

    @GetMapping("/getScoreValue")
    @ResponseBody
    public int getScoreValue() {
        return game.getScoreValue();
    }

    private void enableDebug() {
        showDebug = true ? ! showDebug : showDebug;
    }

    @GetMapping("/debug")
    @ResponseBody
    public String debug() {
        enableDebug();
        return mainHTMLgen();
    }

    private StringBuilder title() {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Minesweeper</h1>");
        return html;
    }

    @GetMapping("/getTableHtml")
    @ResponseBody
    public String mainHTMLgen() {
        StringBuilder html = new StringBuilder();
        html
                .append(applyCSSproduction(html))
                .append("<div class='main'>")
                .append(title())
                .append(createMineBoard())
                .append(isGameOver())
                .append(generateScoreBoard())
                .append(debugButton())
                .append(printDebugPanel())
                .append(credits())
                .append("</div>");
        return html.toString();
    }

    private String printDebugPanel() {
        return showDebug == true ? "<div class='debug'><p>" + game.toString().replace("\n", "<br>") + "</p></div>" : "";
    }

    private StringBuilder debugButton() {
        StringBuilder html = new StringBuilder();
        html.append("<div class='debugButton'>");
        html.append("<form action='/debug' method='get'>");
        html.append("<input class='debugInput' type='submit' value='\uD83D\uDEE0\uFE0F Debug panel'>");
        html.append("</form>");
        html.append("</div>");
        return html;
    }

    private StringBuilder generateScoreBoard() {
        StringBuilder html = new StringBuilder();
        if (game.isGameOver() || game.isWin()) {
            game.saveWinner();
            html.append("<table class='hallFame'>");
            html.append("<tr><th>ID</th><th>name</th><th>Date</th><th>score</th></tr>");
            for (String[] row : game.getHallFame()) {
                String id = row[0];
                String name = row[1];
                String date = row[2];
                String score = row[3];

                // TODO: Not working.

                html
                        .append("<tr><td>")
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
        return html;
    }

    private StringBuilder isGameOver() {
        StringBuilder html = new StringBuilder();
        if (game.isGameOver()) {
            html
                    .append("<form action='/gameOver.html' method='get'>")
                    .append("<input class='gameover' type='submit' value='Game Over'>")
                    .append("</form>");
        } else if (game.isWin()) {
            html
                    .append("<form  action='/win.html' method='get'>")
                    .append("<input class='gameover' type='submit' value='You Win'>")
                    .append("</form>");
        }
        return html;
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
        cell.append("<a href='/revealCell?row=").append(row).append("&column=").append(column).append("'>");
        if (game.isHidden(row, column)) {
            cell.append("<div class='cell'>");
        } else {
            cell.append("<div class='cellLight'>");
        }
        if (! game.isHidden(row, column)) {
            if (game.isMine(row, column)) {
                final String mine = "assets/bomb.webp";
                cell.append("<img class='mine' src='" + mine + "' alt='mine'>");
            } else {
                switch (game.getCell(row, column).getMinesAround()) {
                    case 0 -> cell.append("<span class='numero zero'>").append("</span>");
                    case 1 -> cell
                                      .append("<span class='numero one'>")
                                      .append(game.getCell(row, column).getMinesAround())
                                      .append("</span>");
                    case 2 -> cell
                                      .append("<span class='numero two'>")
                                      .append(game.getCell(row, column).getMinesAround())
                                      .append("</span>");
                    case 3 -> cell
                                      .append("<span class='numero three'>")
                                      .append(game.getCell(row, column).getMinesAround())
                                      .append("</span>");
                    case 4 -> cell
                                      .append("<span class='numero four'>")
                                      .append(game.getCell(row, column).getMinesAround())
                                      .append("</span>");
                    case 5 -> cell
                                      .append("<span class='numero five'>")
                                      .append(game.getCell(row, column).getMinesAround())
                                      .append("</span>");
                    case 6 -> cell
                                      .append("<span class='numero six'>")
                                      .append(game.getCell(row, column).getMinesAround())
                                      .append("</span>");
                    case 7 -> cell
                                      .append("<span class='numero seven'>")
                                      .append(game.getCell(row, column).getMinesAround())
                                      .append("</span>");
                    case 8 -> cell
                                      .append("<span class='numero eight'>")
                                      .append(game.getCell(row, column).getMinesAround())
                                      .append("</span>");
                }
            }
        }

        cell.append("</div>").append("</a>");
        return cell;
    }
}
