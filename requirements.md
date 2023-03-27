# Minesweeper

## Project Objective

In this project, we will develop a game where the player has to find a series of hidden mines in a grid of cells. The
game will provide a hint to the player by indicating the number of mines in the adjacent cells when they uncover a cell.

The objective of the game is to uncover all the cells without mines, without uncovering any cell that has a mine (this
will result in losing the game).

## Requirements

### Specification

The game is based on a 6 x 6 cell matrix (i.e., 6 rows and 6 columns) initially, but the code should be written in such
a way that it can be adapted to any number of rows and columns. At the beginning of the game, a certain number of
mines (initially 8, but this number should also be customizable) will be randomly distributed in the cells, and the
player must find the cells where the mines are hidden. A cell can be in one of three different states:

- **Covered**: This is the initial state of the cell, and in this state, the player does not know whether there is a
  hidden mine in the cell or not.
- **Marked**: This state is the same as the previous one, but the player has marked the cell to indicate that they think
  there is a mine in that cell.
- **Uncovered**: If the player opens the cell, it enters this state. If there is a mine in the cell, opening the cell
  will automatically result in losing the game. If there is no mine, the game will show the player how many mines are in
  the adjacent cells. To make the game easier, if the number of adjacent cells is zero, the game will automatically open
  all the adjacent cells that are not yet uncovered, as we know for sure that there are no mines in them (this
  functionality will not be implemented in the first phase of the project).

The player wins if they manage to uncover all the cells without mines. At each moment in the game, we must show the
board to the player, so they can see the uncovered cells and the ones that are still covered.

### Classes to Implement

The implementation of the game will be based on the following two classes:

- **Cell** class: This class implements a cell on the board and will include the following attributes:
    - *mined*: A boolean that indicates whether the cell contains a mine or not.
    - *state*: An integer that indicates the state of the cell. Three constants will be defined in this class to
      represent the different states: Covered (1), marked (2), and uncovered (3).
    - *row*: The number of the row where the cell is placed.
    - *column*: The number of the column where the cell is placed.
- **Game** class: This class has the attributes "cells" (the matrix of Cell objects), "rows," and "columns" (which
  indicate the number of rows and columns in the matrix). We only need to add the getters for these two attributes
  because we cannot change the number of rows and columns once the game has started. It will also include the following
  methods:
    - *public Cell **getCell**(int row, int column*): Returns the cell that is in a specific row and column.
    - *private ArrayList<Cell> **getAdjacentCells**(Cell cell)*: Obtains the list of adjacent cells to the cell received
      as a parameter.
    - *public int **getAdjacentMines**(Cell cell)*: Returns the sum of mines in the adjacent cells to the cell received
      as a parameter. It will use the previous method.
    - *public void **uncoverCell**(Cell cell)*: Uncovers a cell, and if the number of adjacent mines is zero, it
      uncovers all the adjacent cells that are not uncovered yet. It will also use the *getAdjacentCells()* method, and
      it is important to highlight that, by its own definition, it is a recursive method, since it calls itself in its
      own code.
    - *public void **uncoverAllCells()***: Uncovers all cells that have mines. It will be used when the player loses to
      show them where the mines were.
    - *public boolean **checkCellsToOpen()***: Checks if there are cells without mines to uncover. It will be used to
      know whether the player won the game.
    - *private void **fillMines**(int mines)*: Places the indicated number of mines randomly in the cells array.
    - *public **Game**(int rows, int columns, int mines)*: Creates a new game. It initializes the cells matrix and uses
      the previous method to distribute the mines. 