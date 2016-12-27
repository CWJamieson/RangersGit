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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DeleteScreen extends AppCompatActivity {

    ArrayList<TextView> texts = new ArrayList<TextView>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> planners = new ArrayList<String>();
    ArrayList<String> flags = new ArrayList<String>();
    String deleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Delete contacts");
        char [] prefs = getIntent().getCharArrayExtra("PREFS");
        boolean displayAlert = prefs[0]=='0';
        if(displayAlert) {
            alert();
            prefs[0]='1';
            writePrefs(prefs);
        }


        String [] planners = this.getIntent().getStringArrayExtra("PLANNERS");
        String [] flags = this.getIntent().getStringArrayExtra("FLAGS");
        String [] friends = this.getIntent().getStringArrayExtra("FRIENDS");
        deleted = " ";
        for(int i=0;i<friends.length;i++)
        {
            names.add(friends[i]);
            this.planners.add(planners[i]);
            this.flags.add(flags[i]);
        }
        createFabs();
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
    private void createFabs()
    {
        for(int i=0;i<names.size();i++)
        {
            FloatingActionButton fab = new  FloatingActionButton(this);
            TextView text = new TextView(this);
            text.setText(names.get(i));
            texts.add(text);
            fab.setId(i);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((FloatingActionButton)view).setRippleColor(Color.GREEN);
                    deleteFromFile(view.getId());
                    view.setEnabled(false);
                    ((FloatingActionButton) view).hide();
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
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==android.R.id.home) {
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }
        return true;

    }
    private void deleteFromFile(int num)
    {
        String fullString;
        deleted = deleted+num+" ";
        for(TextView text : texts)
        {
            if(text.getText().equals(names.get(num)))
                text.setText("");
        }

        deleteFile();
        for(int i=0;i<flags.size();i++) {

                if(!deleted.contains(" "+i+" ")) {
                    //Add data, flag and name
                    fullString = planners.get(i) + "~" + flags.get(i) + "~" + names.get(i);
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
