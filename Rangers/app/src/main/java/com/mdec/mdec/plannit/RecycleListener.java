package com.mdec.mdec.plannit;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Christopher on 12/22/2016.
 */

public class RecycleListener implements View.OnClickListener {

    //loc location number in list, planners: array of binary planners. context: app context, friends: array of friend names, prefs: binary preference data
    private int loc;
    private String [] planners;
    private Context context;
    private String [] friends;
    private char [] prefs;
    private String comeFrom;

    public RecycleListener(int loc, String [] planners, String [] friends, char [] prefs, Context context, String comeFrom)
    {
        //set defaults
        this.loc = loc;
        this.planners = planners;
        this.context = context;
        this.friends = friends;
        this.prefs = prefs;
        this.comeFrom  = comeFrom;
    }

    public void onClick(View v)
    {
        if(comeFrom.equals("DELETE"))
        {

        }
        else if(comeFrom.equals("EDIT"))
        {
            //activity.edit();
        }
        else
        {
            Intent intent = new Intent(context, DisplayScreen.class);
            intent.putExtra("PLANNER", planners[loc]);
            intent.putExtra("NAME", friends[loc]);
            intent.putExtra("PREFS", prefs);
            context.startActivity(intent);
        }
    }
}
