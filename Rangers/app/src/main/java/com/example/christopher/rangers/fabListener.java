package com.example.christopher.rangers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import static java.security.AccessController.getContext;

/**
 * Created by Christopher on 12/22/2016.
 */

public class fabListener implements View.OnClickListener {
    int loc;
    String [] planners;
    Context context;
    String [] friends;
    char [] prefs;
    public fabListener(int loc, String [] planners, String [] friends, char [] prefs, Context context)
    {
        this.loc = loc;
        this.planners = planners;
        this.context = context;
        this.friends = friends;
        this.prefs = prefs;
    }
    public void onClick(View v)
    {
        ((FloatingActionButton) v).setRippleColor(Color.WHITE);
            Intent intent = new Intent(context, DisplayScreen.class);
            intent.putExtra("PLANNER", planners[loc]);
            intent.putExtra("NAME", friends[loc]);
            intent.putExtra("PREFS", prefs);
            context.startActivity(intent);

    }
}
