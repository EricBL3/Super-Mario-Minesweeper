/*
 * This class controls all the logic of the game
 */
package logic;

import java.io.*;

/**
 *
 * @author Eric
 */
public class GameManager implements Serializable{
    private Cell[][] grid;
    private int ren;
    private int col;
    private int flagsLeft;
    private int minesLeft;
    private boolean gameOver;
    private boolean won;
    private int cellsLeft;
    private String difficulty;
    private int timePassed;
    
    
    
    public GameManager(String difficulty)
    {
        this.difficulty = difficulty;
        int minesToPlace;
        timePassed = 0;
        gameOver = false;
        won = false;
        switch (difficulty) 
        {
            case "easy":
                ren = 8;
                col = 8;
                
                //cells left is # of cells without mines 
                cellsLeft = (ren*col)-10;
                flagsLeft = 10;
                minesLeft = 10;
                //col is x and ren is y
                grid = new Cell[col][ren];
                setGrid();
                minesToPlace = 10;
                while(minesToPlace > 0)
                {
                   
                    minesToPlace = placeMines(minesToPlace);
                }  
                break;
            case "medium":
                ren = 16;
                col = 16;
                cellsLeft = (ren*col)-40;
                flagsLeft = 40;
                minesLeft = 40;
                //col is x and ren is y
                grid = new Cell[col][ren];
                setGrid();
                minesToPlace = 40;
                while(minesToPlace > 0)
                {
                    minesToPlace = placeMines(minesToPlace);
                } 
                break;
            case "hard":
                ren = 24;
                col = 24;
                cellsLeft = (ren*col)-99;
                flagsLeft = 99;
                minesLeft = 99;
                //col is x and ren is y
                grid = new Cell[col][ren];
                setGrid();
                minesToPlace = 99;
                while(minesToPlace > 0)
                {
                    minesToPlace = placeMines(minesToPlace);
                }  
                break;
            default:
                break;
        }
    }
    
    public void setGrid()
    {
        for(int i = 0; i < col; i++)
            {
                for(int j = 0; j < ren; j++)
                {
                    grid[i][j] = new Cell(col, ren);
                }
            }
    }
    
    public void setGrid(Cell[][] savedGrid)
    {
        grid = savedGrid;
    }
            
    
    public Cell[][] getGrid()
    {
        return grid;
    }   
    
    public void setMinesLeft(int minesLeft)
    {
        this.minesLeft = minesLeft;
    }
    
    public int getMinesLeft()
    {
        return minesLeft;
    }
    
    public void setFlagsLeft(int flagsLeft)
    {
        this.flagsLeft = flagsLeft;
    }
    
    public int getFlagsLeft()
    {
        return flagsLeft;
    }
    
    public void setCellsLeft(int cellsLeft)
    {
        this.cellsLeft = cellsLeft;
    }   
    
    public int getCellsLeft()
    {
        return cellsLeft;
    }
    
    public void setGameOver(boolean val)
    {
        gameOver = val;
    }
    
    public boolean getGameOver()
    {
        return gameOver;
    }
    
    public void setWon(boolean val)
    {
        won = val;
    }
    
    public boolean getWon()
    {
        return won;
    }
    
    public int getRen()
    {
        return ren;
    }
    
    public String getDifficulty()
    {
        return difficulty;
    }
    
    public void setTimePassed(int time)
    {
        timePassed += time;
    }
    
    public int getTimePassed()
    {
        return timePassed;
    }
    
    //This method is used to place the mines in the grid
    public int placeMines(int minesToPlace)
    {
        double prob = (double)minesToPlace/(double)(ren*col);
        for(int i = 0; i < col; i++)
        {
            for(int j = 0; j < ren; j++)
            {
                if(minesToPlace > 0 && (Math.random()*1) <= prob && !grid[i][j].getHasMine())
                {
                    grid[i][j].setHasMine(true);
                    minesToPlace--;
                }
            }
        }
        return minesToPlace;
    }
    
    //This method checks if the cell has a mine 
    public void checkCell(int[] loc)
    {
        if(!grid[loc[0]][loc[1]].getIsOpen())
        {
            setCellsLeft(getCellsLeft()-1);
            grid[loc[0]][loc[1]].setIsOpen(true);
        }
        //loc[0] is x and loc[1] is y
        if(grid[loc[0]][loc[1]].getHasMine())
        {
            gameOver = true;
        }
    }
    
    //This method checks how many mines are surrounding the selected cell
    public void checkCollidingCells(int[] loc)
    {
        int counter = 0;
            //check if there are cells surrounding the selected cell and if it has a mine.
            if(loc[0]-1 >= 0 && loc[1]-1 >= 0 && grid[loc[0]-1][loc[1]-1].getHasMine())
                counter++;
            if(loc[1]-1 >= 0 && grid[loc[0]][loc[1]-1].getHasMine())
                counter++;
            if(loc[0]+1 < col && loc[1]-1 >= 0 && grid[loc[0]+1][loc[1]-1].getHasMine())
                counter++;
            if(loc[0]-1 >= 0 && grid[loc[0]-1][loc[1]].getHasMine())
                counter++;
            if(loc[0]+1 < col && grid[loc[0]+1][loc[1]].getHasMine())
                counter++;
            if(loc[0]-1 >= 0 && loc[1]+1 < ren && grid[loc[0]-1][loc[1]+1].getHasMine())
                counter++;
            if(loc[1]+1 < ren && grid[loc[0]][loc[1]+1].getHasMine())
                counter++;
            if(loc[0]+1 < col && loc[1]+1 < ren && grid[loc[0]+1][loc[1]+1].getHasMine())
                counter++;
            
        grid[loc[0]][loc[1]].setSurroundingMines(counter);
    }
    
    public void placeFlag(int[] loc)
    {
        grid[loc[0]][loc[1]].setIsFlagged(true);
        flagsLeft--;
    }
    
    public void removeFlag(int[] loc)
    {
        grid[loc[0]][loc[1]].setIsFlagged(false);
        flagsLeft++;
    }
    
    public void openSurroundingCells(int[] loc)
    {
        //check if there are cells surrounding the selected cell and open them
        int[] newLoc = new int[2];
        if(loc[0]-1 >= 0 && loc[1]-1 >= 0 && !grid[loc[0]-1][loc[1]-1].getIsOpen())
        {
            grid[loc[0]-1][loc[1]-1].setIsOpen(true);
            setCellsLeft(getCellsLeft()-1);
            //set new location for checking colliding cells
            newLoc[0] = loc[0]-1;
            newLoc[1] = loc[1]-1;
            checkCollidingCells(newLoc);
        }
        if(loc[1]-1 >= 0 && !grid[loc[0]][loc[1]-1].getIsOpen())
        {
            grid[loc[0]][loc[1]-1].setIsOpen(true);
            setCellsLeft(getCellsLeft()-1);
            newLoc[0] = loc[0];
            newLoc[1] = loc[1]-1;
            checkCollidingCells(newLoc);
        }
        if(loc[0]+1 < col && loc[1]-1 >= 0 && !grid[loc[0]+1][loc[1]-1].getIsOpen())
        {
            grid[loc[0]+1][loc[1]-1].setIsOpen(true);
            setCellsLeft(getCellsLeft()-1);
            newLoc[0] = loc[0]+1;
            newLoc[1] = loc[1]-1;
            checkCollidingCells(newLoc);
        }
        if(loc[0]-1 >= 0 && !grid[loc[0]-1][loc[1]].getIsOpen())
        {
            grid[loc[0]-1][loc[1]].setIsOpen(true);
            setCellsLeft(getCellsLeft()-1);
            newLoc[0] = loc[0]-1;
            newLoc[1] = loc[1];
            checkCollidingCells(newLoc);
        }
        if(loc[0]+1 < col && !grid[loc[0]+1][loc[1]].getIsOpen())
        {
            grid[loc[0]+1][loc[1]].setIsOpen(true);
            setCellsLeft(getCellsLeft()-1);
            newLoc[0] = loc[0]+1;
            newLoc[1] = loc[1];
            checkCollidingCells(newLoc);
        }
        if(loc[0]-1 >= 0 && loc[1]+1 < ren && !grid[loc[0]-1][loc[1]+1].getIsOpen())
        {
            grid[loc[0]-1][loc[1]+1].setIsOpen(true);
            setCellsLeft(getCellsLeft()-1);
            newLoc[0] = loc[0]-1;
            newLoc[1] = loc[1]+1;
            checkCollidingCells(newLoc);
        }
        if(loc[1]+1 < ren && !grid[loc[0]][loc[1]+1].getIsOpen())
        {
            grid[loc[0]][loc[1]+1].setIsOpen(true);
            setCellsLeft(getCellsLeft()-1);
            newLoc[0] = loc[0];
            newLoc[1] = loc[1]+1;
            checkCollidingCells(newLoc);
        }
        if(loc[0]+1 < col && loc[1]+1 < ren && !grid[loc[0]+1][loc[1]+1].getIsOpen())
        {
            grid[loc[0]+1][loc[1]+1].setIsOpen(true);
            setCellsLeft(getCellsLeft()-1);
            newLoc[0] = loc[0]+1;
            newLoc[1] = loc[1]+1;
            checkCollidingCells(newLoc);
        }
    }
    
    public void checkOpenCells()
    {
        if(cellsLeft == 0 && flagsLeft == 0)
        {
            won = true;
            gameOver = true;
        }
    }
    
          
}
