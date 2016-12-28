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

    //count: button number on page, page: fragment number, clicked: boolean array for if a button is clicked
    private int count, page;
    private boolean [] clicked;

    //set defaults
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
            v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        }
        else
        {
            clicked[count + ((page - 1) * 28)] = false;
            v.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
        }

    }
}
