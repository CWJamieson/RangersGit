package com.example.christopher.rangers;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeleteScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Delete contacts");

        boolean displayAlert = true;
        if(displayAlert)
            alert();


        String [] friends = getIntent().getStringArrayExtra("FRIENDS");
        for(int i=0;i<friends.length;i++)
        {
            FloatingActionButton fab = new  FloatingActionButton(this);
            TextView text = new TextView(this);
            text.setText(friends[i]);
            fab.setId(i);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((FloatingActionButton)view).setRippleColor(Color.GREEN);
                    deleteFromFile(view.getId());
                }
            });
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
            finish();
        }
        return true;

    }
    private void deleteFromFile(int num)
    {
        //delete numth line from file
    }
}
