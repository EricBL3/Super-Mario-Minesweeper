/*
 * This class allows the user to save their game progress.
 */
package logic;
import java.io.*;

/**
 *
 * @author Eric
 */
public class FileWriter {
    
    
    public boolean saveGame(GameManager myGame, String save)
    {
        try
        {
            FileOutputStream file = null;
            
            if(save.equals("save1"))
                file = new FileOutputStream("saves/save1.ser");
            else if(save.equals("save2"))
                file = new FileOutputStream("saves/save2.ser");
            else if(save.equals("save3"))
                file = new FileOutputStream("saves/save3.ser");           
            
            
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(myGame);
            out.writeObject(myGame.getGrid());
           
            
            out.close();
            file.close();

            System.out.println("The game has been serialized");
        }
        catch(Exception e)
        {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        }
        finally
        {
            return true;
        }
    }
    
    public boolean saveInfo(GameManager myGame, String save)
    {
        String fileContent = save + "," + myGame.getDifficulty() + "," + myGame.getTimePassed() + "," + myGame.getFlagsLeft();
        try
        {
            FileOutputStream outputStream = null;
            if(save.equals("save1"))
                outputStream = new FileOutputStream("saves/save1Info.txt");
            else if(save.equals("save2"))
                outputStream = new FileOutputStream("saves/save2Info.txt");
            else if(save.equals("save3"))
                outputStream = new FileOutputStream("saves/save3Info.txt");
            
            byte[] strToBytes = fileContent.getBytes();
            outputStream.write(strToBytes);

            outputStream.close();

            System.out.println("The game info has been saved to a text file");
        }
        catch (Exception e)
        {
            //error while saving the books data
            e.printStackTrace();
            return false;
        }
        finally
        {
            return true;
        }
    }
}
