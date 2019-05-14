/*
 This class models a Cell from the minesweeper game grid
 */
package logic;
import java.io.*;
/**
 *
 * @author Eric
 */
public class Cell implements Serializable{
    private boolean hasMine;
    private boolean isFlagged;
    private boolean isOpen;
    private int[] location;
    private int surroundingMines;
    private boolean guiOpen;
    
    public Cell(int x, int y)
    {
        isFlagged = false;
        isOpen = false;
        guiOpen = false;
        location = new int[2];
        location[0] = x;
        location[1] = y;
        surroundingMines = 0;
    }
    
    public void setHasMine(boolean val)
    {
        hasMine = val;
    }
    
    public boolean getHasMine()
    {
        return hasMine;
    }
    
    public void setIsFlagged(boolean val)
    {
        isFlagged = val;
    }
    
    public boolean getIsFlagged()
    {
        return isFlagged;
    }
    
    public int[] getLocation()
    {
        return location;
    }
    
    public void setIsOpen(boolean val)
    {
        isOpen = val;
    }
    
    public boolean getIsOpen()
    {
        return isOpen;
    }
    
    public void setGuiOpen(boolean val)
    {
        guiOpen = val;
    }
    
    public boolean getGuiOpen()
    {
        return guiOpen;
    }
    
    public void setSurroundingMines(int mines)
    {
        surroundingMines = mines;
    }
    
    public int getSurroundingMines()
    {
        return surroundingMines;
    }
}
