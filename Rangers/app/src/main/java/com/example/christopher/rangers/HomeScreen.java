package com.example.christopher.rangers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.christopher.rangers.R.drawable.ic_check;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String [] friends;
    String [] planners;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Plannit");

        //Read input from save file
        ArrayList<ArrayList<String>> string = readFile();
        //Parallel arrays for each line
        //Name
        String [] friends = string.get(2).toArray(new String[string.get(2).size()]);
        //Data
        String [] planners = string.get(0).toArray(new String[string.get(0).size()]);
        //Flag(type)
        String [] flag = string.get(1).toArray(new String[string.get(1).size()]);

        boolean displayAlert = true;
        if(displayAlert)
            alert();

        this.friends = friends;
        this.planners = planners;
        for(int i=0;i<friends.length;i++)
        {
            FloatingActionButton fab = new  FloatingActionButton(this);
            TextView text = new TextView(this);
            text.setMinLines(2);
            text.setText(friends[i]);

            fabListener list = new fabListener(i, planners, friends, this);
            fab.setOnClickListener(list);
            //Todo: change icon if its a group
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void alert()
    {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("This is an app for finding free time with friends. You'll want to " +
                "start by inputting your own schedule, using the \"Input Planner\" option. All help" +
                " messages will only display once automatically, but can be brought up using the top" +
                " corner.");
        dlgAlert.setTitle("Welcome to Plannit!");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //write to never show again
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
    //Back button is pressed
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, DeleteScreen.class);
            intent.putExtra("FRIENDS",friends);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.help)
        {
            alert();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_input) {
            getInput();
        } else if (id == R.id.nav_createGroup) {
            createGroup();
        } else if (id == R.id.nav_share) {
            share();
        } else if (id == R.id.nav_read) {
            read();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void read()
    {
        Intent intent = new Intent(this, ReadScreen.class);
        startActivity(intent);
    }
    private void share()
    {
        Intent intent = new Intent(this, ShareScreen.class);
        intent.putExtra("PLANNER", planners[0]);
        startActivity(intent);
    }
    private void getInput()
    {
        Intent intent = new Intent(this, EnterSchedule.class);
        startActivity(intent);
    }

    private void createGroup()
    {
        Intent intent = new Intent(this, group.class);

        intent.putExtra("FRIENDS", friends);
        intent.putExtra("PLANNERS", planners);
        startActivity(intent);
    }
    private void deleteContacts()
    {
        Toast.makeText(this, "Test", Toast.LENGTH_LONG).show();

    }

    protected ArrayList<ArrayList<String>> readFile()
    {
        //Create double Arraylist (0 for data, 1 for names)
        ArrayList<ArrayList<String>> string = new ArrayList<>();
        string.add(new ArrayList<String>());
        string.add(new ArrayList<String>());
        string.add(new ArrayList<String>());
        String lineString = "";
        String fileName = "saveFile";
        File file = new File(getApplicationContext().getFilesDir(), fileName);
        FileInputStream fin = null;
        int character;
        try
        {
            fin = new FileInputStream(file);
            while((character = fin.read()) != -1)
            {
                //When it hits end of line flush
                if(Character.toString((char)character).equals("\n"))
                {
                    //Splits line at name and data
                    String temp[] = lineString.split("~");
                    //Store data
                    string.get(0).add(temp[0]);
                    //Store flag
                    string.get(1).add(temp[1]);
                    //Store name
                    string.get(2).add(temp[2]);
                    //Reset line
                    lineString = "";
                }
                else
                {
                    //Add character to line
                    lineString = lineString + Character.toString((char)character);
                }
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
        //Return parsed data
        return string;
    }
}
