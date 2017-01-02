package com.example.christopher.rangers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SaveScreen extends AppCompatActivity implements OnItemSelectedListener
{
    protected String item;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set title
        setTitle("Choose a name");

        //read prefs
        char [] prefs = getIntent().getCharArrayExtra("PREFS");
        boolean displayAlert = prefs[6]=='0';
        if(displayAlert) {
            alert();
            prefs[6]='1';
            writePrefs(prefs);
        }

        //add editText listener to respond to enter
        TextView.OnEditorActionListener enterListener = new TextView.OnEditorActionListener()
        {
            public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    save(item);
                }
                return true;
            }
        };
        EditText edit = (EditText) findViewById(R.id.edit_message);
        edit.setOnEditorActionListener(enterListener);

        //Create spinner and its listener
        Spinner spinner = (Spinner) findViewById(R.id.colorSpinner);
        spinner.setOnItemSelectedListener(this);

        //Create list for spinner
        ArrayList<String> colorList = new ArrayList<>();
        colorList.add("Blue");
        colorList.add("Cyan");
        colorList.add("Dark Grey");
        colorList.add("Light Grey");
        colorList.add("Grey");
        colorList.add("Green");
        colorList.add("Magenta");
        colorList.add("Red");
        colorList.add("Yellow");
        colorList.add("Black");

        //Put list into spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colorList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        //create fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save(item);
            }
        });

    }

    //display alert message
    private void alert()
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

    //create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.help_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
            int id = item.getItemId();

        if (id==R.id.help)
        {
            alert();
        }
        return true;

    }

    //set save data
    private void save(String color) {
        String name;
        //Fetch boolean array clicked from schedule
        String saveString = this.getIntent().getStringExtra("BUTTON_STATUS");
        String flagString = this.getIntent().getStringExtra("FLAG");
        String colorString = "";

        switch (color)
        {
            case "Blue":
                colorString = "b";
                break;
            case "Cyan":
                colorString = "c";
                break;
            case "Dark Grey":
                colorString = "d";
                break;
            case "Light Grey":
                colorString = "l";
                break;
            case "Grey":
                colorString = "e";
                break;
            case "Green":
                colorString = "g";
                break;
            case "Magenta":
                colorString = "m";
                break;
            case "Red":
                colorString = "r";
                break;
            case "Yellow":
                colorString = "y";
                break;
            default:
                colorString = "k";
                break;

        }

        //Retrieve string from input and add to saveString
        EditText nameView = (EditText) findViewById(R.id.edit_message);
        name = nameView.getText().toString();
        if(name.equals(""))
        {
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_LONG).show();
        }
        else
        {
            //Add data, flag and name
            saveString = saveString + "~" + flagString + "~" + colorString + "~" + name;
            //Save to file
            fileSave(saveString);
            //Return to home screen
            Intent intent = new Intent(this, HomeScreen.class);
            this.startActivity(intent);
        }
    }
    //save to file
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

    //refresh preferences
    private void writePrefs(char [] in)
    {
        //Save to personal file
        FileOutputStream fos = null;
        FileIO fileIO = new FileIO(getApplicationContext());
        String fileName = "preferences";
        File file = new File(getApplicationContext().getFilesDir(), fileName);
        file.delete();
        String fileSaveString = "";
        for(int i=0;i<in.length;i++)
        {
            fileSaveString +=in[i];
        }
        try
        {

            Log.d("fileSaveString", fileSaveString);
            //Create file
            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            //Write to file
            fos.write(fileSaveString.getBytes());

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}