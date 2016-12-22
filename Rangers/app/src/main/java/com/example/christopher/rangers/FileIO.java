package com.example.christopher.rangers;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Bradley on 2016-12-22.
 */

public class FileIO {
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
}
