package com.example.christopher.rangers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class group extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        //Todo: generate intersection
        String intersection = "Temp";
        Intent intent = new Intent(this, SaveScreen.class);
        intent.putExtra("BUTTON_STATUS", intersection);
        startActivity(intent);
    }
    protected void displayFriends()
    {
        Intent intent = getIntent();
        String [] friends = intent.getStringArrayExtra("FRIENDS");
        for(int i=0;i<friends.length;i++)
        {
            CheckBox chk = new CheckBox(this);
            chk.setText(friends[i]);

            LinearLayout layout = (LinearLayout) findViewById(R.id.content_group);
            layout.addView(chk);
        }
    }

}
