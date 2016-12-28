package com.example.christopher.rangers;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.christopher.rangers.R.drawable.ic_check;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String [] friends;
    String [] planners;
    String [] flags;
    char[] prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Plannit");

        //Read input from save file
        ArrayList<ArrayList<String>> string = readFile();
        prefs = readPrefs();
        //Parallel arrays for each line
        //Name
        String [] friends = string.get(2).toArray(new String[string.get(2).size()]);
        //Data
        String [] planners = string.get(0).toArray(new String[string.get(0).size()]);
        //Flag(type)
        String [] flag = string.get(1).toArray(new String[string.get(1).size()]);



        boolean displayAlert = prefs[4]=='0';
        if(displayAlert) {
            alert();
            prefs[4]='1';
            writePrefs(prefs);
        }

        this.friends = friends;
        this.planners = planners;
        this.flags = flag;
        for(int i=0;i<friends.length;i++)
        {
            FloatingActionButton fab = new  FloatingActionButton(this);
            TextView text = new TextView(this);
            text.setMinLines(2);
            text.setText(friends[i]);

            fabListener list = new fabListener(i, planners, friends, prefs, this);
            fab.setOnClickListener(list);
            if(flag[i].equals("g"))
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
            intent.putExtra("FLAGS", flags);
            intent.putExtra("PLANNERS", planners);
            intent.putExtra("PREFS", prefs);
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

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if(permissionCheck != getPackageManager().PERMISSION_GRANTED)
        {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {


                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
        else
        {

            Intent intent = new Intent(this, ReadScreen.class);
            intent.putExtra("PREFS", prefs);
            startActivity(intent);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(this, ReadScreen.class);
                    intent.putExtra("PREFS", prefs);
                    startActivity(intent);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private void share()
    {
        Intent intent = new Intent(this, ShareScreen.class);
        intent.putExtra("PREFS", prefs);
        intent.putExtra("PLANNER", planners[0]);
        startActivity(intent);
    }
    private void getInput()
    {
        Intent intent = new Intent(this, EnterSchedule.class);
        intent.putExtra("PREFS", prefs);
        startActivity(intent);
    }

    private void createGroup()
    {
        Intent intent = new Intent(this, group.class);

        intent.putExtra("PREFS", prefs);
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
    private char [] readPrefs()
    {
        //Create double Arraylist (0 for data, 1 for names)
        char [] out;
        String lineString = "";
        String fileName = "preferences";
        File file = new File(getApplicationContext().getFilesDir(), fileName);
        if(!file.exists())
        {
            //Save to personal file
            FileOutputStream fos = null;
            FileIO fileIO = new FileIO(getApplicationContext());
            file.delete();
            String fileSaveString = "";
            for(int i=0;i<8;i++)
            {
                fileSaveString+="0";
            }

            try
            {

                Log.d("fileSaveString", fileSaveString);
                //Create file
                fos = openFileOutput(fileName, Context.MODE_PRIVATE);
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
            return fileSaveString.toCharArray();

        }
        else {
            FileInputStream fin = null;
            int character;
            try {
                fin = new FileInputStream(file);
                while ((character = fin.read()) != -1) {
                    //When it hits end of line flush
                    if (Character.toString((char) character).equals("\n")) {
                        break;
                    } else {
                        //Add character to line
                        lineString = lineString + Character.toString((char) character);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fin != null) {
                        fin.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            out = lineString.toCharArray();
            //Return parsed data
            return out;
        }
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
}
