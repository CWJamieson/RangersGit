package com.example.christopher.rangers;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_schedule);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


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

    }
    public void save()
    {

        Intent intent = new Intent(this, SaveScreen.class);
        intent.putExtra("BUTTON_STATUS", clicked);
        startActivity(intent);
    }

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_enter_schedule, container, false);
            List<Button> buttons = new ArrayList<Button>();
            int count=0;
            for(int id : BUTTON_IDS) {
                Button button = (Button)rootView.findViewById(id);
                button.setText(BUTTON_TEXTS[count]);

               // button.setOnClickListener(new NumberedListener(count, getArguments().getInt(ARG_SECTION_NUMBER),getArguments().getBooleanArray((CLICKED))));

                buttons.add(button);
                count++;
            }
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
            init(clicked);
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1,(String) getPageTitle(position), clicked);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
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
