package com.example.christopher.rangers;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DisplayScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("NAME"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        char [] prefs = getIntent().getCharArrayExtra("PREFS");
        boolean displayAlert = prefs[1]=='0';
        if(displayAlert) {
            alert();
            prefs[1]='1';
            writePrefs(prefs);
        }

        LinearLayout layout;
        String planner = getIntent().getStringExtra("PLANNER");
        while(planner.length()<140)
            planner = planner+"0";
        TextView text = new TextView(this);

        createHeaders();
        layout = (LinearLayout) findViewById(R.id.col1);
        for(int j=0;j<BUTTON_TEXTS.length;j++) {
            text = new TextView(this);
            if(getWindowManager().getDefaultDisplay().getWidth()>1080)
                text.setText((BUTTON_TEXTS[j]+"m"));
            else
                text.setText((BUTTON_TEXTS[j]));
            layout.addView(text);
        }
        for(int i=1;i<6;i++)
        {
            switch(i)
            {
                case 1:
                    layout = (LinearLayout) findViewById(R.id.col2);
                    break;
                case 2:
                    layout = (LinearLayout) findViewById(R.id.col3);
                    break;
                case 3:
                    layout = (LinearLayout) findViewById(R.id.col4);
                    break;
                case 4:
                    layout = (LinearLayout) findViewById(R.id.col5);
                    break;
                default:
                    layout = (LinearLayout) findViewById(R.id.col6);
                    break;
            }

            for(int j=0;j<BUTTON_TEXTS.length;j++) {
                text = new TextView(this);
                text.setText(" ..... ");
                if(planner.charAt((i-1)*28+j)=='0')
                    text.setBackgroundColor(Color.GREEN);
                else
                    text.setBackgroundColor(Color.WHITE);

                layout.addView(text);
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.help_menu, menu);
        return true;
    }private void writePrefs(char [] in)
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
    private void createHeaders()
    {

        String [] headers = {"Day","Mon","Tues","Wed","Thurs","Fri"};
        TextView text2 = new TextView(this);
        LinearLayout layout = (LinearLayout) findViewById(R.id.col1);
        text2.setText(headers[0]);
        layout.addView(text2);
        text2 = new TextView(this);
        layout = (LinearLayout) findViewById(R.id.col2);
        text2.setText(headers[1]);
        layout.addView(text2);
        text2 = new TextView(this);
        layout = (LinearLayout) findViewById(R.id.col3);
        text2.setText(headers[2]);
        layout.addView(text2);
        text2 = new TextView(this);
        layout = (LinearLayout) findViewById(R.id.col4);
        text2.setText(headers[3]);
        layout.addView(text2);
        text2 = new TextView(this);
        layout = (LinearLayout) findViewById(R.id.col5);
        text2.setText(headers[4]);
        layout.addView(text2);
        text2 = new TextView(this);
        layout = (LinearLayout) findViewById(R.id.col6);
        text2.setText(headers[5]);
        layout.addView(text2);

    }
    private void returnToMain()
    {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }
    private void alert()
    {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("The grid represents all time during the week in half hour increments" +
                ", available times are shown in green while the rest of the week is shown in white");
        dlgAlert.setTitle("Planner display");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //write to never show again
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==android.R.id.home) {
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }
        else if (id==R.id.help)
        {
            alert();
        }
        return true;

    }
    private static final String[] BUTTON_TEXTS = {
            "8:00a",
            "8:30a",
            "9:00a",
            "9:30a",
            "10:00a",
            "10:30a",
            "11:00a",
            "11:30a",
            "12:00p",
            "12:30p",
            "1:00p",
            "1:30p",
            "2:00p",
            "2:30p",
            "3:00p",
            "3:30p",
            "4:00p",
            "4:30p",
            "5:00p",
            "5:30p",
            "6:00p",
            "6:30p",
            "7:00p",
            "7:30p",
            "8:00p",
            "8:30p",
            "9:00p",
            "9:30p",

    };

}
