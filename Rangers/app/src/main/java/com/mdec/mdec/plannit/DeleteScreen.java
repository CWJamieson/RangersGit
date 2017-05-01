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
import java.util.Arrays;

public class DeleteScreen extends AppCompatActivity {

    //globals for contact data
    private ArrayList<TextView> texts = new ArrayList<TextView>();
    ArrayList<ContactObj> contacts = new ArrayList<ContactObj>();
    private char [] prefs;
    String deleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_screen);

        //add back button & title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Delete Contacts");

        //read contact data
        Bundle b = this.getIntent().getExtras();
        contacts = (ArrayList<ContactObj>)b.getSerializable("CONTACTS");


        //read preferences and display help message
        prefs = (char [])b.getSerializable("PREFS");
        boolean displayAlert = prefs[0]=='0';
        if(displayAlert) {
            alert();
            prefs[0]='1';
            writePrefs(prefs);
        }

        deleted = " ";
        //RecyclerView
        RecyclerView recycleView = (RecyclerView)findViewById(R.id.activity_delete_screen);
        recycleView.setHasFixedSize(true);
        //RecyclerView layout manager
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView adapter
        recycleView.setAdapter(new ContactAdapter(this.contacts, this.prefs, this, "DELETE"));
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

    private void warning(String s, final int n)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Are you sure you wish to delete "+s+"? this cannot be undone");
        dlgAlert.setTitle("Deleting "+s);
        dlgAlert.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFromFile(n);
                    }
                });
        dlgAlert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    //help messsage box
    private void alert()
    {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("To delete contacts, simply click all contacts you wish to remove," +
                " and press the arrow at the top to pernamently delete them. To cancel, press" +
                " back on your device.");
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


    //delete methods
    private void deleteFromFile(int num)
    {

        findViewById(num).setEnabled(false);
        ((FloatingActionButton) findViewById(num)).hide();
        String fullString;
        deleted = deleted+num+" ";
        for(TextView text : texts)
        {
            if(text.getText().equals(contacts.get(num).getName()))
                text.setText("");
        }

        deleteFile();
        for(int i=0;i<contacts.size();i++) {

            if(!deleted.contains(" " + i + " "))
            {
                //Add data, flag and name
                fullString = contacts.get(i).getPlanner() + "~" + contacts.get(i).getFlag() + "~" + contacts.get(i).getColor() + "~" + contacts.get(i).getName();
                //Save to file
                fileSave(fullString);
            }
        }
    }

    private void deleteFile()
    {
        String fileName = "saveFile";
        File file = new File(getApplicationContext().getFilesDir(), fileName);
        file.delete();
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
