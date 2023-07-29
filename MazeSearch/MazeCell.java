// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2021T2, Assignment 6
 * Name: Ella Wipatene
 * Username: wipateella
 * ID: 300558005
 */

import ecs100.*;
import java.util.*;
import java.awt.Color;

/**
 * Represents a single cell (square) in a maze.
 * It is a node in a graph of MazeCells - each MazeCell has a set of neighbours.
 * The useful methods are:
 *  - visit(), unvisit(), and isVisited()
 *    (to record if a MazeCell has been visted during a search)
 *  - draw(Color)  to redraw the cell in a given colour
 */

public class MazeCell implements Iterable<MazeCell>{
    private final int col;
    private final int row;
    private Set<MazeCell> neighbours;

    private boolean visited = false;

    /**
     * Make a MazeCell located at the given row and column
     */
    public MazeCell(int row, int col) {
        this.row = row;
        this.col = col;
        neighbours = new HashSet<MazeCell>(4); 
    }

    /**
     * Visiting and unvisiting
     */
    public void visit()   { this.visited = true; }

    public void unvisit() { this.visited = false; }

    public boolean isVisited() { return visited; }

    /**
     * Allows a foreach loop to step through the neighbours of a MazeCell:
     *  for (MazeCell neigh : cell) {..do something to each neighbour...}
     */
    public Iterator<MazeCell> iterator(){
        return neighbours.iterator();
    }

    /**
     * Redraws a cell in a specified color.
     * The area drawn includes the gap between the cell and its neighbours
     */
    public void draw(Color color) {
        UI.setColor(color);

        double x = Maze.MAZE_LEFT + col * Maze.CELL_SIZE + 1;
        double y = Maze.MAZE_TOP + row * Maze.CELL_SIZE + 1;
        double wd = Maze.CELL_SIZE - 2;
        double ht = Maze.CELL_SIZE - 2;
        UI.fillRect(x, y, wd, ht);

        for (MazeCell neighbour : neighbours){
            if (neighbour.row == row-1)      {UI.fillRect(x, y-2, wd, 2); }// neighbour above
            else if (neighbour.row == row+1) {UI.fillRect(x, y+ht,wd, 2); }// neighbour below
            else if (neighbour.col == col-1) {UI.fillRect(x-2, y, 2, ht); }// neighbour left
            else if (neighbour.row == row+1) {UI.fillRect(x+wd,y, 2, ht); }// neighbour right
        }
    }

    // ============ USED ONLY FOR CONSTRUCTING THE MAZE ==================

    /**
     * Get row and column
     */
    public int getRow() { return row; }

    public int getCol() { return col; }

    /**
     * Add a neighbour,
     * used when building the graph
     */
    public void addNeighbour(MazeCell neigh){
        neighbours.add(neigh);
    }

    /**
     * toString for debugging
     */
    public String toString() {
        return String.format("%d,%d (%b)", col, row, visited);
    }

}
