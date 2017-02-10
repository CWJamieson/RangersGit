package com.mdec.mdec.plannit;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Calendar;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>
{

    private String [] friends;
    private String [] planners;
    private String [] flags;
    private String [] colors;
    private char [] prefs;
    private Context context;
    private String comeFrom;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public CardView cardView;
        public TextView name;
        public ImageView icon;

        public ViewHolder(View v)
        {
            super(v);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            name = (TextView) v.findViewById(R.id.contact_name);
            icon = (ImageView) v.findViewById(R.id.contact_icon);
        }
    }

    public ContactAdapter(String[] friends, String [] planners, String[] flags, String[] colors, char[] prefs, Context context, String comeFrom)
    {
        this.friends = friends;
        this.planners = planners;
        this.flags = flags;
        this.colors = colors;
        this.prefs = prefs;
        this.context = context;
        this.comeFrom = comeFrom;
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        CardView itemView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_home_screen, parent, false);

        return new ContactAdapter.ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //Find current date and time
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 20);
        c.set(Calendar.MINUTE, 21);
        int day = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int pos = -1;
        if((day>=Calendar.MONDAY) && (day <= Calendar.FRIDAY))
        {
            if(hour>=8 && hour<=21)
            {
                pos = (28*(day-2))+(2*(hour-8))+(min/30);
            }
        }

        //Set icon based on flag
        if (flags[position].equals("g"))
        {
            holder.icon.setImageResource(R.drawable.ic_people);
        }
        else
        {
            holder.icon.setImageResource(R.drawable.ic_person);
        }
        //Set icon color
        if(planners[position].charAt(pos) == '0')
        {
            holder.icon.setColorFilter(Color.GREEN);
        }
        else
        {
            holder.icon.setColorFilter(Color.WHITE);
        }

        //Set name
        holder.name.setText(friends[position]);
        //Set color of button
        if(colors[position].equals("b"))
        {
            holder.cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.BLUE));
        }
        else if(colors[position].equals("c"))
        {
            holder.cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.CYAN));
        }
        else if(colors[position].equals("d"))
        {
            holder.cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.DKGRAY));
        }
        else if(colors[position].equals("l"))
        {
            holder.cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.LTGRAY));
        }
        else if(colors[position].equals("e"))
        {
            holder.cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.GRAY));
        }
        else if(colors[position].equals("g"))
        {
            holder.cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.GREEN));
        }
        else if(colors[position].equals("m"))
        {
            holder.cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.MAGENTA));
        }
        else if(colors[position].equals("r"))
        {
            holder.cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.RED));
        }
        else if(colors[position].equals("y"))
        {
            holder.cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.YELLOW));
        }
        else
        {
            holder.cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.BLACK));
        }
        //Set listener
        RecycleListener list = new RecycleListener(position, planners, friends, prefs, context, comeFrom);
        holder.cardView.setOnClickListener(list);
    }

    @Override
    public int getItemCount()
    {
        return friends.length;
    }
}
