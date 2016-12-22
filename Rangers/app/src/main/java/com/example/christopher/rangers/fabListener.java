package com.example.christopher.rangers;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import static java.security.AccessController.getContext;

/**
 * Created by Christopher on 12/22/2016.
 */

public class fabListener implements View.OnClickListener {
    int loc;
    String [] planners;
    Context context;
    public fabListener(int loc, String [] planners, Context context)
    {
        this.loc = loc;
        this.planners = planners;
        this.context = context;
    }
    public void onClick(View v)
    {
            Intent intent = new Intent(context, DisplayScreen.class);
            intent.putExtra("PLANNER", planners[loc]);
            context.startActivity(intent);

    }
}
