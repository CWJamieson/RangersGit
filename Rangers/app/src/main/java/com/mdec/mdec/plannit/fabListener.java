package com.mdec.mdec.plannit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

/**
 * Created by Christopher on 12/22/2016.
 */

public class fabListener implements View.OnClickListener {

    //loc fab location, number in list, planners: array of binary planners. context: app context, friends: array of friend names, prefs: binary preference data
    private int loc;
    private String [] planners;
    Context context;
    private String [] friends;
    char [] prefs;
    public fabListener(int loc, String [] planners, String [] friends, char [] prefs, Context context)
    {
        //set defaults
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
