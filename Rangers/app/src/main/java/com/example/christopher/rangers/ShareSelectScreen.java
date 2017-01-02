package com.example.christopher.rangers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ShareSelectScreen extends AppCompatActivity {

    //texts: contact name textviews, names: name Strings, planners: binary planner strings, flags: flag strings, colors: color choice, prefs: binary preference data
    ArrayList<TextView> texts = new ArrayList<TextView>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> planners = new ArrayList<String>();
    ArrayList<String> flags = new ArrayList<String>();
    String [] colors;

    char [] prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_select_screen);

        //set title and back button
        setTitle("Share a contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //read preferences
        prefs = getIntent().getCharArrayExtra("PREFS");
        boolean displayAlert = prefs[8]=='0';
        if(displayAlert) {
            alert();
            prefs[8]='1';
            writePrefs(prefs);
        }

        //set data
        String [] planners = this.getIntent().getStringArrayExtra("PLANNERS");
        String [] flags = this.getIntent().getStringArrayExtra("FLAGS");
        String [] friends = this.getIntent().getStringArrayExtra("FRIENDS");
        String [] colors = this.getIntent().getStringArrayExtra("COLORS");
        for(int i=0;i<friends.length;i++)
        {
            names.add(friends[i]);
            this.planners.add(planners[i]);
            this.flags.add(flags[i]);
            this.colors = colors;
        }
        //create contact icons
        createFabs();
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

    //create contact icons
    private void createFabs()
    {
        for(int i=0;i<names.size();i++)
        {
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
                    share(view.getId());
                }
            });
            if(flags.get(i).equals("g"))
                fab.setImageResource(R.drawable.ic_people);
            else
                fab.setImageResource(R.drawable.ic_person);
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
    }

    //proceed to next screen
    private void share(int i)
    {
        Intent intent = new Intent(this, ShareScreen.class);
        intent.putExtra("PREFS", prefs);
        intent.putExtra("PLANNER", planners.get(i));
        startActivity(intent);
    }

    //display help message
    private void alert()
    {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Here you can select a contact to share with friends, silmply tap an" +
                " icon and follow the instructions on the next page");
        dlgAlert.setTitle("Sharing contacts");
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
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==android.R.id.home) {
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }
        return true;

    }
}
