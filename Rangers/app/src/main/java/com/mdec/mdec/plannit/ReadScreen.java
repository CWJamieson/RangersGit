package com.mdec.mdec.plannit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReadScreen extends AppCompatActivity {
    //prefs: binary preference data
    char [] prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set back button and title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Scan a plan code");

        //read prefs
        prefs = getIntent().getCharArrayExtra("PREFS");
        boolean displayAlert = prefs[5]=='0';
        if(displayAlert) {
            alert();
            prefs[5]='1';
            writePrefs(prefs);
        }



        //start camera read
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();

    }

    //display help message
    private void alert()
    {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("To read a friend's planner, simply have them share their planner" +
                " using the \"share\" option on the home screen and view the qr code with this app");
        dlgAlert.setTitle("Scanning a code");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
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

    //create menu
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
        else if (id==android.R.id.home) {
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }
        return true;

    }

    //upon reading a code
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                finish();
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String res = result.getContents();
                //error check
                if(res.length()!=140)
                {

                    Toast.makeText(this, "That is not a Plannit Code", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
                for(int i=0;i<res.length();i++)
                {
                    if(res.charAt(i)!='0' && res.charAt(i)!='1')
                    {
                        Toast.makeText(this, "That is not a Plannit Code", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                try {
                    //Integer.parseInt(result.getContents());
                    Intent intent = new Intent(this, SaveScreen.class);
                    intent.putExtra("BUTTON_STATUS", res);
                    intent.putExtra("PREFS", prefs);
                    intent.putExtra("FLAG", "f");
                    startActivity(intent);
                }
                catch(Exception e)
                {

                    Toast.makeText(this, "That is not a Plannit Code", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
