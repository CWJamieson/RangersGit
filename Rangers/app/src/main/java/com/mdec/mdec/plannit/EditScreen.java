package com.mdec.mdec.plannit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    ArrayList<ContactObj> contacts = new ArrayList<ContactObj>();
    char [] prefs;
    ArrayList<String> colors = new ArrayList<String>();
    String deleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);

        //add back button & title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Edit Contacts");

        //read preferences and display help message
        prefs = getIntent().getCharArrayExtra("PREFS");

        boolean displayAlert = prefs[9]=='0';
        if(displayAlert) {
            alert();
            prefs[9]='1';
            writePrefs(prefs);
        }

        //read contact data
        Bundle b = this.getIntent().getExtras();
        contacts = (ArrayList<ContactObj>)b.getSerializable("CONTACTS");
        deleted = " ";
        //RecyclerView
        RecyclerView recycleView = (RecyclerView)findViewById(R.id.activity_edit_screen);
        recycleView.setHasFixedSize(true);
        //RecyclerView layout manager
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView adapter
        recycleView.setAdapter(new ContactAdapter(this.contacts, this.prefs, this, "EDIT"));
        //create contact buttons
        //createFabs();
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
        String s = contacts.get(num).getPlanner();
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
        dlgAlert.setMessage("To re-edit a schedule, click any name to change and re-save it. It will create a new contact, " +
                "so the old one will need to be deleted manually");
        dlgAlert.setTitle("Editing contacts");
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
