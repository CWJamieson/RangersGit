package com.example.christopher.rangers;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static java.security.AccessController.getContext;

/**
 * Created by Christopher on 12/22/2016.
 */

public class ButtonListener  implements View.OnClickListener{
    int count, page;
    boolean [] clicked;
    public ButtonListener(int count, int page, boolean [] clicked)
    {
        this.count = count;
        this.page = page;
        this.clicked= clicked;
    }
    @Override
    public void onClick(View v)
    {
//Switch statement to figure out which button in the array to click based on fragment
        int local = count;
        Log.d("Count", ""+local);
        if(!clicked[count + ((page-1)*28)]) {
            clicked[count + ((page - 1) * 28)] = true;
            ((Button) v).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        }
        else
        {
            clicked[count + ((page - 1) * 28)] = false;
            ((Button) v).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        }
        /*
        switch (page)
        {

            //Monday
            case 1:
                clicked[count + 0] = true;
                ((Button) v).setBackgroundColor(Color.RED);
                break;
            //Tuesday
            case 2:
                clicked[count+ 28] = true;
                ((Button) v).setBackgroundColor(Color.BLACK);
                break;
            //Wednesday
            case 3:
                clicked[count + 56] = true;
                ((Button) v).setBackgroundColor(Color.GREEN);
                break;
            //Thursday
            case 4:
                clicked[count + 84] = true;
                ((Button) v).setBackgroundColor(Color.BLUE);
                break;
            //Friday
            case 5:
                clicked[count + 112] = true;
                ((Button) v).setBackgroundColor(Color.CYAN);
                break;
        }
        */
    }
}
