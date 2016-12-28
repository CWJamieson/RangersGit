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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.R.attr.id;

public class group extends AppCompatActivity {
    char [] prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefs = getIntent().getCharArrayExtra("PREFS");
        boolean displayAlert = prefs[3]=='0';
        if(displayAlert) {
            alert();
            prefs[3]='1';
            writePrefs(prefs);
        }
        setTitle("Create Group");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create group
                createGroup();
            }
        });
        fab.setImageResource(R.drawable.ic_add);
        displayFriends();
    }
    private void createGroup()
    {
        Intent intent = getIntent();
        String [] friends = intent.getStringArrayExtra("PLANNERS");
        char[] intersection = new char[140];
        boolean flag = true;
        int count=0;
        for(int i=0;i<140;i++)
            intersection[i] = '0';
        for(int i=0;i<friends.length;i++)
        {
            CheckBox chk = (CheckBox)findViewById(i);
            if(chk.isChecked())
            {
                count++;
                for(int j=0;j<140;j++)
                {
                    if(!(intersection[j] == '0' && friends[i].charAt(j) == '0'))
                        intersection[j] = '1';
                }
            }
        }
        String out = "";

        for(int i=0;i<140;i++)
        {
            out+=intersection[i];
            if(intersection[i]=='0')
                flag=false;
        }
        //Todo: check for empty or full
        if(count==0)
        {
            Toast.makeText(this, "You must select a contact or hit the back button", Toast.LENGTH_LONG).show();
        }
        else if(flag)
        {
            Toast.makeText(this, "There is no overlap of free time in the selected group, sorry", Toast.LENGTH_LONG).show();
        }
        else {
            intent = new Intent(this, SaveScreen.class);
            intent.putExtra("BUTTON_STATUS", out);
            intent.putExtra("PREFS", prefs);
            intent.putExtra("FLAG", "g");
            startActivity(intent);
        }
    }
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

        else if (id==android.R.id.home) {
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }
        return true;

    }
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
    private void alert()
    {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("To create a group, select the contacts or groups you wish to include," +
                " and press the check when you are finished.");
        dlgAlert.setTitle("Creating a group");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //write to never show again
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
    protected void displayFriends()
    {
        Intent intent = getIntent();
        String [] friends = intent.getStringArrayExtra("FRIENDS");
        for(int i=0;i<friends.length;i++)
        {
            CheckBox chk = new CheckBox(this);
            chk.setText(friends[i]);
            chk.setId(i);
            LinearLayout layout = (LinearLayout) findViewById(R.id.content_group);
            layout.addView(chk);
        }
    }

}
