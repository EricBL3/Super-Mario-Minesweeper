/*
 * This class allows the user to open saved games and open their info.
 */
package logic;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Eric
 */
public class FileReader {
    public GameManager loadGameManager(String save)
    {
        GameManager loadedGame = null;
        boolean loaded = false;
        try
            {
                FileInputStream file = null;
                if(save.equals("save1"))
                    file = new FileInputStream("saves/save1.ser");
                else if(save.equals("save2"))
                    file = new FileInputStream("saves/save2.ser");
                else if(save.equals("save3"))
                    file = new FileInputStream("saves/save3.ser");
                
                ObjectInputStream in = new ObjectInputStream(file);

                loadedGame = (GameManager)in.readObject();
                
                in.close();
                file.close();

                System.out.println("The game manager has been deserialized");
                loaded = true;
            }
            catch(IOException e)
            {
                System.out.println("IOException caught while loading");
                e.printStackTrace();
            }
            catch(ClassNotFoundException e)
            {
                System.out.println ("ClassNotFoundException caught");
                e.printStackTrace();
            }
        if(loaded)
            return loadedGame;
        else 
            return null;
    }
    
    public Cell[][] loadGameGrid(String save)
    {
        Cell[][] savedGrid = null;
        boolean loaded = false;
        try
            {
                FileInputStream file = null;
                if(save.equals("save1"))
                    file = new FileInputStream("saves/save1.ser");
                else if(save.equals("save2"))
                    file = new FileInputStream("saves/save2.ser");
                else if(save.equals("save3"))
                    file = new FileInputStream("saves/save3.ser");
                
                ObjectInputStream in = new ObjectInputStream(file);
                in.readObject();
                savedGrid = (Cell[][])in.readObject();
                
                in.close();
                file.close();

                System.out.println("The game grid has been deserialized");
                loaded = true;
            }
            catch(IOException e)
            {
                System.out.println("IOException caught while loading grid");
                e.printStackTrace();
            }
            catch(ClassNotFoundException e)
            {
                System.out.println ("ClassNotFoundException caught");
                e.printStackTrace();
            }
        if(loaded)
            return savedGrid;
        else 
            return null;
    }
    
    public String loadInfo(String savePath)
    {
        String info = "";
        try
	{
            File myFile = new File(savePath);
            Scanner scan = new Scanner(myFile);
            while(scan.hasNextLine())
            {
		info = scan.nextLine();
            }
            scan.close();
			
	}
        catch(FileNotFoundException e)
        {
            System.out.println("file not found...");
            
            e.printStackTrace();
        }
        catch(IOException e)
	{
            System.out.println("Something went wrong...");
            e.printStackTrace();
	}
        return info;
    }
    
}
