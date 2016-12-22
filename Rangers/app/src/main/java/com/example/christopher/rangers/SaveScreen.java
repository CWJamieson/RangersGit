package com.example.christopher.rangers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class SaveScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });
    }
    private void save()
    {
        String saveString = "";
        //Fetch boolean array clicked from schedule
        boolean boolArray[] = this.getIntent().getBooleanArrayExtra("BUTTON_STATUS");
        //Transform boolean array into a string of data for file saving
        for(boolean booleans : boolArray)
        {
            //Button was clicked
            if(booleans)
            {
                saveString = saveString + "1";
            }
            //Button was not clicked
            else
            {
                saveString = saveString + "0";
            }
        }
        //Retrieve string from input and add to saveString
        EditText nameView = (EditText) findViewById(R.id.edit_message);
        saveString = saveString + " " + nameView.getText().toString();
        //Save to file
        fileSave(saveString);
        //Return to home screen
        Intent intent = new Intent(this, HomeScreen.class);
        this.startActivity(intent);
    }

    private void fileSave(String saveString)
    {
        //Save to personal file
        FileOutputStream fos = null;
        try
        {
            //String that includes the contents for the whole file (new and old)
            String fileSaveString = saveString ;//+ getOldContents();
            //Create file
            fos = openFileOutput("saveFile", Context.MODE_PRIVATE);
            //Write to file
            fos.write(fileSaveString.getBytes());
            //Show user save was successful
            Toast toast = Toast.makeText(getBaseContext(),"Schedule Saved",Toast.LENGTH_SHORT);
            toast.show();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (fos != null)
                {
                    fos.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /*private String getOldContents()
    {
        String oldString = "";
        File storage = getApplicationInfo().dataDir;
        FileInputStream fin = null;
        int character;
        try
        {
            File file = new File(storage, "saveFile");
            fin = openFileInput(file);
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
        return oldString;
    }*/
}