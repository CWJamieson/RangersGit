package com.example.christopher.rangers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import static android.R.attr.id;

public class group extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean displayAlert = true;
        if(displayAlert)
            alert();

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
        for(int i=0;i<140;i++)
            intersection[i] = '1';
        for(int i=0;i<friends.length;i++)
        {
            CheckBox chk = (CheckBox)findViewById(i);
            if(chk.isChecked())
            {
                for(int j=0;j<140;j++)
                {
                    if(!(intersection[j] == '1' && friends[i].charAt(j) == '1'))
                        intersection[j] = '0';
                }
            }
        }
        String out = "";
        for(int i=0;i<140;i++)
        {
            out+=intersection[i];
        }
        //Todo: check for empty or full
        intent = new Intent(this, SaveScreen.class);
        intent.putExtra("BUTTON_STATUS", out);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.help_menu, menu);
        return true;
    }public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==R.id.help)
        {
            alert();
        }
        return true;

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
