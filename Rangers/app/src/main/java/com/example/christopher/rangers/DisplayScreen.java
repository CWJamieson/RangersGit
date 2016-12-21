package com.example.christopher.rangers;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        LinearLayout layout;
        String planner = getIntent().getStringExtra("PLANNER");
        planner = "10010000011001000001100100000110010000011001000001100100000110010000011001000001100100000110010000011001000001100100000110010000011001000001";
        TextView text = new TextView(this);

        createHeaders();
        layout = (LinearLayout) findViewById(R.id.col1);
        for(int j=0;j<BUTTON_TEXTS.length;j++) {
            text = new TextView(this);
            text.setText(BUTTON_TEXTS[j]);

            layout.addView(text);
        }
        for(int i=1;i<6;i++)
        {
            switch(i)
            {
                case 1:
                    layout = (LinearLayout) findViewById(R.id.col2);
                    break;
                case 2:
                    layout = (LinearLayout) findViewById(R.id.col3);
                    break;
                case 3:
                    layout = (LinearLayout) findViewById(R.id.col4);
                    break;
                case 4:
                    layout = (LinearLayout) findViewById(R.id.col5);
                    break;
                default:
                    layout = (LinearLayout) findViewById(R.id.col6);
                    break;
            }

            for(int j=0;j<BUTTON_TEXTS.length;j++) {
                text = new TextView(this);
                text.setText(" ..... ");
                if(planner.charAt((i-1)*28+j)=='1')
                    text.setBackgroundColor(Color.GREEN);
                else
                    text.setBackgroundColor(Color.WHITE);

                layout.addView(text);
            }
        }

    }
    private void createHeaders()
    {

        String [] headers = {"Day","Mon","Tues","Wed","Thurs","Fri"};
        TextView text2 = new TextView(this);
        LinearLayout layout = (LinearLayout) findViewById(R.id.col1);
        text2.setText(headers[0]);
        layout.addView(text2);
        text2 = new TextView(this);
        layout = (LinearLayout) findViewById(R.id.col2);
        text2.setText(headers[1]);
        layout.addView(text2);
        text2 = new TextView(this);
        layout = (LinearLayout) findViewById(R.id.col3);
        text2.setText(headers[2]);
        layout.addView(text2);
        text2 = new TextView(this);
        layout = (LinearLayout) findViewById(R.id.col4);
        text2.setText(headers[3]);
        layout.addView(text2);
        text2 = new TextView(this);
        layout = (LinearLayout) findViewById(R.id.col5);
        text2.setText(headers[4]);
        layout.addView(text2);
        text2 = new TextView(this);
        layout = (LinearLayout) findViewById(R.id.col6);
        text2.setText(headers[5]);
        layout.addView(text2);

    }
    private void returnToMain()
    {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;

    }
    private static final String[] BUTTON_TEXTS = {
            "8:00am",
            "8:30am",
            "9:00am",
            "9:30am",
            "10:00am",
            "10:30am",
            "11:00am",
            "11:30am",
            "12:00pm",
            "12:30pm",
            "1:00pm",
            "1:30pm",
            "2:00pm",
            "2:30pm",
            "3:00pm",
            "3:30pm",
            "4:00pm",
            "4:30pm",
            "5:00pm",
            "5:30pm",
            "6:00pm",
            "6:30pm",
            "7:00pm",
            "7:30pm",
            "8:00pm",
            "8:30pm",
            "9:00pm",
            "9:30pm",

    };

}
