package com.example.christopher.rangers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnterSchedule extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    char [] prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_schedule);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Input a planner");

        prefs = getIntent().getCharArrayExtra("PREFS");
        boolean displayAlert = prefs[2]=='0';
        if(displayAlert) {
            alert();
            prefs[2]='1';
            writePrefs(prefs);
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        init(clicked);
        Log.d("This shit jsut happend", "It refreshed");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setImageResource(R.drawable.ic_check);

    }private void writePrefs(char [] in)
    {
        //Save to personal file
        FileOutputStream fos = null;
        FileIO fileIO = new FileIO(getApplicationContext());
        String fileName = "preferences";
        File file = new File(getApplicationContext().getFilesDir(), fileName);
        file.delete();
        String fileSaveString = "";
        for(int i=0;i<in.length;i++)
        {
            fileSaveString +=in[i];
        }
        try
        {

            Log.d("fileSaveString", fileSaveString);
            //Create file
            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
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
    private void alert()
    {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("This is where you input your own schedule. click the times you are busy, " +
                "and swipe left and right to view different days of the week. Press the check when" +
                "you are finished.");
        dlgAlert.setTitle("Entering your planner");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //write to never show again
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
    public void save()
    {

        Intent intent = new Intent(this, SaveScreen.class);
        intent.putExtra("PREFS", prefs);
        String saveString = "";
        //Transform boolean array into a string of data for file saving
        for(boolean booleans : clicked)
        {
            //Button was clicked
            if(booleans)
            {
                saveString = saveString + "1";
            }
            //Button was not clicked
            else
            {
                saveString = saveString + "0";
            }
        }
        saveString = saveString;
        intent.putExtra("BUTTON_STATUS", saveString);
        intent.putExtra("FLAG", "m");
        startActivity(intent);
    }
   /* @Override
    public void onPause()
    {

    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.help)
        {
            alert();
        }

        return super.onOptionsItemSelected(item);
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

    private static final int[] BUTTON_IDS = {
            R.id.Button1,
            R.id.Button2,
            R.id.Button3,
            R.id.Button4,
            R.id.Button5,
            R.id.Button6,
            R.id.Button7,
            R.id.Button8,
            R.id.Button9,
            R.id.Button10,
            R.id.Button11,
            R.id.Button12,
            R.id.Button13,
            R.id.Button14,
            R.id.Button15,
            R.id.Button16,
            R.id.Button17,
            R.id.Button18,
            R.id.Button19,
            R.id.Button20,
            R.id.Button21,
            R.id.Button22,
            R.id.Button23,
            R.id.Button24,
            R.id.Button25,
            R.id.Button26,
            R.id.Button27,
            R.id.Button28,

    };
    boolean clicked[] = new boolean[140];
    public void init(boolean clicked[])
    {
        for(int i=0;i<140;i++)
        {
            clicked[i] = false;
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String CLICKED = "clicked";
        private static final String ARG_SECTION_NAME = "section_name";

        public PlaceholderFragment() {

            setRetainInstance(true);
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String sectionName, boolean clicked[]) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_SECTION_NAME, sectionName);
            args.putBooleanArray(CLICKED, clicked);
            fragment.setArguments(args);
            return fragment;
        }

        protected Button button;
        protected List<Button> buttons = new ArrayList<Button>();
        protected int count;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_enter_schedule, container, false);
            count=0;

            buttons = new ArrayList<Button>();
            for(int i=0;i<BUTTON_IDS.length;i++) {
                button = (Button)rootView.findViewById(BUTTON_IDS[i]);
                button.setText(BUTTON_TEXTS[count]);
                //Button Listener that handles color change and selection change
                if(getArguments().getBooleanArray((CLICKED))[i + (getArguments().getInt(ARG_SECTION_NUMBER)-1)*28])
                {
                    button.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                }
                ButtonListener but = new ButtonListener(i, getArguments().getInt(ARG_SECTION_NUMBER),  getArguments().getBooleanArray((CLICKED)));
                button.setOnClickListener(but);


                buttons.add(button);
                count++;
            }
            count=0;
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getArguments().getString(ARG_SECTION_NAME));

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1,(String) getPageTitle(position), clicked);
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MONDAY";
                case 1:
                    return "TUESDAY";
                case 2:
                    return "WEDNESDAY";
                case 3:
                    return "THURSDAY";
                case 4:
                    return "FRIDAY";
            }
            return null;
        }
    }
}
