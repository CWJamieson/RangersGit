package com.mdec.mdec.plannit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EditScreen extends AppCompatActivity {

    //globals for contact data
    ArrayList<TextView> texts = new ArrayList<TextView>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> planners = new ArrayList<String>();
    ArrayList<String> flags = new ArrayList<String>();
    char prefs [];
    String [] colors;
    String deleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);

        //add back button & title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Delete contacts");

        //read preferences and display help message
        char [] prefs = getIntent().getCharArrayExtra("PREFS");
        this.prefs = prefs;
        boolean displayAlert = prefs[9]=='0';
        if(displayAlert) {
            alert();
            prefs[9]='1';
            writePrefs(prefs);
        }

        //read contact data
        String [] planners = this.getIntent().getStringArrayExtra("PLANNERS");
        String [] flags = this.getIntent().getStringArrayExtra("FLAGS");
        String [] friends = this.getIntent().getStringArrayExtra("FRIENDS");
        String [] colors = this.getIntent().getStringArrayExtra("COLORS");
        deleted = " ";
        for(int i=0;i<friends.length;i++)
        {
            names.add(friends[i]);
            this.planners.add(planners[i]);
            this.flags.add(flags[i]);
            this.colors = colors;
        }

        //create contact buttons
        createFabs();
    }

    //create menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.help_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        //back button
        if (id==android.R.id.home) {
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }
        //hamburger helper
        else if(id==R.id.help)
        {
            alert();
        }
        return true;

    }

    //update preferences file
    private void writePrefs(char [] in)
    {
        //Save to personal file
        FileOutputStream fos = null;
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

    //create contact buttons
    private void createFabs()
    {
        //cycle through data
        for(int i=0;i<names.size();i++)
        {
            //create button set info and text
            FloatingActionButton fab = new  FloatingActionButton(this);
            TextView text = new TextView(this);
            text.setText(names.get(i));
            texts.add(text);
            HomeScreen.buttonColorSet(fab, colors, i);
            fab.setId(i);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((FloatingActionButton)view).setRippleColor(Color.GREEN);
                    edit(view.getId());
                }
            });

            //choose icon
            if(flags.get(i).equals("g"))
                fab.setImageResource(R.drawable.ic_people);
            else
                fab.setImageResource(R.drawable.ic_person);

            //select column
            LinearLayout layout;
            if(i%4==0)
                layout = (LinearLayout) findViewById(R.id.col1);
            else if(i%4==1)
                layout = (LinearLayout) findViewById(R.id.col2);
            else if(i%4==2)
                layout = (LinearLayout) findViewById(R.id.col3);
            else
                layout = (LinearLayout) findViewById(R.id.col4);
            layout.addView(fab);
            layout.addView(text);

        }
        if(names.size() == 0)
        {
            LinearLayout layout = (LinearLayout) findViewById(R.id.col1);
            TextView text = new TextView(this);
            text.setText(R.string.nocontacts);
            layout.addView(text);
        }

    }

    public void init(boolean clicked[], String s)
    {

        for(int i=0;i<140;i++)
        {

            clicked[i] = (s.charAt(i)=='1');
        }
    }
    private void edit(int num)
    {
        boolean clicked [] = new boolean [140];
        String s = planners.get(num);
        init(clicked, s);
        Intent intent = new Intent(this, EnterSchedule.class);
        intent.putExtra("PLANNER", clicked);
        intent.putExtra("PREFS", prefs);
        startActivity(intent);
    }


    //help messsage box
    private void alert()
    {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("To re-edit a schedule, click any name to change and re-save it. It will create a new contact," +
                "so the old one will need to be deleted manually");
        dlgAlert.setTitle("Deleting contacts");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //write to never show again
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
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
