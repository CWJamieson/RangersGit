package com.mdec.mdec.plannit;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Bradley on 2016-12-22.
 */
//bradley's file io class (ask him)
public class FileIO
{
    //The context from activity we came from
    Context context;

    public FileIO(Context context)
    {
        this.context = context;
    }

    /**
     * Gets the old contents of the save file
     * @return returns the string of contents from the old file
     */
    protected String getOldContents()
    {
        String oldString = "";
        String fileName = "saveFile";
        File file = new File(context.getFilesDir(), fileName);
        FileInputStream fin = null;
        int character;
        try
        {
            fin = new FileInputStream(file);
            //Get a string with the contents of the folder
            while((character = fin.read()) != -1)
            {
                oldString = oldString + Character.toString((char)character);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (fin != null)
                {
                    fin.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        Log.d("oldString", oldString);
        return oldString;
    }

    /**
     * Removes the contact from the save file
     * @param removeLineNum number of the line to remove
     * @return a string containing the contents of the file with line rmoved
     */
    protected String removeFileLine(int removeLineNum)
    {
        int counter = 0;
        String fileString = "";
        String fileName = "saveFile";
        File file = new File(context.getFilesDir(), fileName);
        FileInputStream fin = null;
        int character;
        try
        {
            fin = new FileInputStream(file);
            //Get a string with the contents of the folder
            while((character = fin.read()) != -1)
            {
                //Hits end of line
                if((char)character == '\n')
                if(Character.toString((char)character).equals("\n"))
                {
                    counter++;
                    if(counter != removeLineNum)
                    {
                        fileString = fileString + Character.toString((char)character);
                    }
                }
                //Finds line to remove
                if(counter != removeLineNum && !Character.toString((char)character).equals("\n"))
                {
                    fileString = fileString + Character.toString((char)character);
                }
            }
            Log.d("Debug", "cnt "+counter+" fileString "+fileString);

        }
        catch(Exception e)
        {

            Log.d("Debug", "cnt "+counter+" fileString ANHDKJHAOIFJOAERROROROEEROROR"+fileString);
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (fin != null)
                {
                    fin.close();
                }

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        return  fileString;
    }

    /**
     * Encrypts the data to save space in save file
     */
 /*   public static String encrypt(String toEncrypt)
    {
        toEncrypt = Base64.encodeToString(toEncrypt.getBytes(), Base64.DEFAULT);
        Log.d("ENCRYPTED", toEncrypt);
        return toEncrypt;
    }
*/
    /**
     * Decrypts the data thats already encrypted
     */
 /*   public static String decrypt(String toDecrypt)
    {
        toDecrypt = new String(Base64.decode(toDecrypt, Base64.DEFAULT));
        Log.d("DECRYPTED", toDecrypt);
        return toDecrypt;
    }
*/
}
