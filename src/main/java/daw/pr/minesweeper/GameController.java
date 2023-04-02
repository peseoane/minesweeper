package daw.pr.minesweeper;

import daw.pr.minesweeper.struct.Cell;
import daw.pr.minesweeper.struct.Difficulty;
import daw.pr.minesweeper.struct.Game;
import daw.pr.minesweeper.struct.StateSelf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    @GetMapping("/startGame")
    @ResponseBody
    public String minesweeper(@RequestParam(value = "difficulty") String difficultyString) {
        LOGGER.info("Starting Minesweeper with difficulty: " + difficultyString);
        Difficulty difficulty = Difficulty.valueOf(difficultyString.toUpperCase());
        Game game = new Game(difficulty);

        int rows = difficulty.getRows();
        int columns = difficulty.getColumns();
        int totalCells = game.getTotalCells();

        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Minesweeper</h1>");
        sb.append("<table>");

        for (Cell[] row : game.getCells()) {
            sb.append("<tr>");
            for (Cell cell : row) {
                sb.append("<td>");
                if (cell.getStateSelf() == StateSelf.MINE) {
                    sb.append("* ");
                } else {
                    sb.append(cell.getMinesAround() + " ");
                }
                sb.append("</td>");
            }
            sb.append("</tr>");
        }

        sb.append("</table>");
        sb.append("<p>Remaining cells: ").append(totalCells).append("</p>");

        return sb.toString();
    }
}
