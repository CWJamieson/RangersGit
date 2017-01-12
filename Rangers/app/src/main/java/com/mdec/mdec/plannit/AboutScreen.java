package com.mdec.mdec.plannit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);

        //add back button & title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("About us");
        TextView info = (TextView) findViewById(R.id.info);
        TextView contact = (TextView) findViewById(R.id.contact);

        //TextView contact2 = (TextView) findViewById(R.id.contact2);
        TextView creds = (TextView) findViewById(R.id.credits);

        info.setText(R.string.infoText);
        contact.setText(R.string.contactText);
        //contact2.setText(R.string.facebook);
        creds.setText(R.string.credsText);


        contact.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        //back button
        if (id==android.R.id.home) {
            finish();
        }
        return true;

    }
}
