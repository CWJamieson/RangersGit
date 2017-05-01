package com.mdec.mdec.plannit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Christopher on 12/22/2016.
 */

public class RecycleListener implements View.OnClickListener {

    //loc location number in list, planners: array of binary planners. context: app context, friends: array of friend names, prefs: binary preference data
    private int loc;
    private Context context;
    private ContactAdapter local;
    private String comeFrom;
    ArrayList<ContactObj> contacts = new ArrayList<>();
    private char [] prefs;
    public RecycleListener(int loc, ArrayList<ContactObj> contacts, char [] prefs, Context context, String comeFrom, ContactAdapter local)
    {
        //set defaults
        this.loc = loc;
        this.contacts = contacts;
        this.context = context;
        this.prefs = prefs;
        this.comeFrom  = comeFrom;
        this.local = local;
    }

    public void onClick(View v)
    {
        if(comeFrom.equals("DELETE"))
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
            dlgAlert.setMessage("Are you sure you wish to delete "+contacts.get(loc).getName()+"? this cannot be undone");
            dlgAlert.setTitle("Deleting "+contacts.get(loc).getName());
            dlgAlert.setPositiveButton("Delete",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            String fullString;
                            deleteFile();
                            for(int i=0;i<contacts.size();i++) {

                                if(!(loc == i))
                                {
                                    //Add data, flag and name
                                    fullString = contacts.get(i).getPlanner() + "~" + contacts.get(i).getFlag() + "~" + contacts.get(i).getColor() + "~" + contacts.get(i).getName();
                                    //Save to file
                                    fileSave(fullString);
                                }
                            }
                            contacts.remove(loc);
                            local.notifyItemRemoved(loc);
                            local.notifyItemRangeChanged(0, contacts.size());


                        }
                    });
            dlgAlert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        else if(comeFrom.equals("EDIT"))
        {

            boolean clicked [] = new boolean [140];
            String s = contacts.get(loc).getPlanner();
            for(int i=0;i<140;i++)
            {
                clicked[i] = (s.charAt(i)=='1');
            }
            Intent intent = new Intent(context, EnterSchedule.class);
            intent.putExtra("PLANNER", clicked);
            intent.putExtra("PREFS", prefs);
            context.startActivity(intent);
        }
        else if(comeFrom.equals("SHARE"))
        {

            Intent intent = new Intent(context, ShareScreen.class);
            intent.putExtra("PREFS", prefs);
            intent.putExtra("PLANNER", contacts.get(loc).getPlanner());
            intent.putExtra("NAME", contacts.get(loc).getName());
            context.startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(context, DisplayScreen.class);
            intent.putExtra("PLANNER", contacts.get(loc).getPlanner());
            intent.putExtra("NAME", contacts.get(loc).getName());
            intent.putExtra("PREFS", prefs);

            context.startActivity(intent);
        }
    }

    private void deleteFile()
    {
        String fileName = "saveFile";
        File file = new File(context.getApplicationContext().getFilesDir(), fileName);
        file.delete();
    }

    private void fileSave(String saveString)
    {
        //Save to personal file
        FileOutputStream fos = null;
        FileIO fileIO = new FileIO(context.getApplicationContext());
        String fileName = "saveFile";
        File file = new File(context.getApplicationContext().getFilesDir(), fileName);
        String fileSaveString;
        try
        {
            //String that includes the contents for the whole file (new and old)
            Log.d("saveString", saveString);
            if(file.length() == 0)
            {
                fileSaveString = saveString + "\n";
            }
            else
            {
                fileSaveString = saveString + "\n" + fileIO.getOldContents();
            }
            Log.d("fileSaveString", fileSaveString);
            //Create file
            fos = context.openFileOutput("saveFile", Context.MODE_PRIVATE);
            //Write to file
            fos.write(fileSaveString.getBytes());

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (fos != null)
                {
                    fos.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
