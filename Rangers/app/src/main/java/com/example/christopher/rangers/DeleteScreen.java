package com.example.christopher.rangers;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeleteScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                    view.setBackgroundColor(Color.GREEN);
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
    private void deleteFromFile(int num)
    {
        //delete numth line from file
    }
}
