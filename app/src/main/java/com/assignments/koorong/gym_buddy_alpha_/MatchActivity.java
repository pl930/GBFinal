package com.assignments.koorong.gym_buddy_alpha_;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.assignments.koorong.gym_buddy_alpha_.Fragments.MatchUserFragment;


public class MatchActivity extends AppCompatActivity {
    private String[] menuOptions;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        FragmentManager fm = getFragmentManager();
        MatchUserFragment MUF = new MatchUserFragment();
        fm.beginTransaction().replace(R.id.content_frame, MUF).commit();
        Toolbar bar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(bar);
        setTitle("Matches");


        menuOptions = getResources().getStringArray(R.array.menu_options);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList = (ListView)findViewById(R.id.left_drawer);
        LayoutInflater inflater = getLayoutInflater();
        View navHead = inflater.inflate(R.layout.nav_header, null, false);

        drawerList.addHeaderView(navHead);

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());


        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, 0, 0){
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                /*getActionBar().setTitle("GymBuddy");*/
            }

            public void onDrawerOpened(View view){
                super.onDrawerOpened(view);
                /*getActionBar().setTitle("Menu");*/
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            swapView(position);
        }
    }

    private void swapView(int position) {
    }
}
