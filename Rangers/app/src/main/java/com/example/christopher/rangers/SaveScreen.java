package com.example.christopher.rangers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SaveScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Choose a name");

        boolean displayAlert = true;
        if(displayAlert)
            alert();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });
    }private void alert()
{

    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
    dlgAlert.setMessage("You will be brought here whenever creating a contact or inputting your" +
            " schedule. just choose a name for the planner and press + when you're ready");
    dlgAlert.setTitle("Adding a contact");
    dlgAlert.setPositiveButton("Ok",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //write to never show again
                }
            });
    dlgAlert.setCancelable(true);
    dlgAlert.create().show();
}
    private void save()
    {
        //Fetch boolean array clicked from schedule
        String saveString = this.getIntent().getStringExtra("BUTTON_STATUS");

        //Retrieve string from input and add to saveString
        EditText nameView = (EditText) findViewById(R.id.edit_message);
        saveString = saveString + "~" + nameView.getText().toString();
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
        FileIO fileIO = new FileIO(getApplicationContext());
        String fileName = "saveFile";
        File file = new File(getApplicationContext().getFilesDir(), fileName);
        String fileSaveString;
        try
        {
            //String that includes the contents for the whole file (new and old)
            Log.d("saveString", saveString);
            if(file.length() == 0)
            {
                fileSaveString = saveString + "\n";
            }
            else
            {
                fileSaveString = saveString + "\n" + fileIO.getOldContents();
            }
            Log.d("fileSaveString", fileSaveString);
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
}