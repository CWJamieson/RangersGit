package com.example.christopher.rangers;

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
    Context context;

    public FileIO(Context context)
    {
        this.context = context;
    }

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

}
