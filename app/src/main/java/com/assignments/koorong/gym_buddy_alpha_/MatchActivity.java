package com.assignments.koorong.gym_buddy_alpha_;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.assignments.koorong.gym_buddy_alpha_.Fragments.HelpFragment;
import com.assignments.koorong.gym_buddy_alpha_.Fragments.MatchUserFragment;
import com.assignments.koorong.gym_buddy_alpha_.Fragments.PreferencesFragment;
import com.assignments.koorong.gym_buddy_alpha_.Fragments.SettingsFragment;
import com.assignments.koorong.gym_buddy_alpha_.Fragments.ShareFragment;
import com.assignments.koorong.gym_buddy_alpha_.NavDrawer.NavDrawerAdapter;
import com.assignments.koorong.gym_buddy_alpha_.NavDrawer.NavDrawerItem;
import com.assignments.koorong.gym_buddy_alpha_.db.UserDataSource;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MatchActivity extends AppCompatActivity {
    private String[] menuOptions;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    FragmentManager fm;

    //Nav Drawer
    private ArrayList<NavDrawerItem> navItems;
    private NavDrawerAdapter adapter;
    private String[] navMenuHeadings;
    private String[] navMenuSubheadings;
    private TypedArray navMenuIcons;

    UserDataSource ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fm = getFragmentManager();
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
        ds = new UserDataSource(this);
        String appUserName = ds.getAppUser().getFirstName() + " " + ds.getAppUser().getLastName();
        drawerList.addHeaderView(navHead);

        navMenuHeadings = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuSubheadings = getResources().getStringArray(R.array.nav_drawer_subheads);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        navItems = new ArrayList<>();

        for(int i = 0; i<4; i++){
            navItems.add(new NavDrawerItem(navMenuIcons.getResourceId(i,-1), navMenuHeadings[i], navMenuSubheadings[i]));
        }

        navMenuIcons.recycle();
        adapter = new NavDrawerAdapter(getApplicationContext(), R.layout.drawer_list_item, navItems);
        drawerList.setAdapter(adapter);
        drawerList.setLongClickable(true);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());


        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, 0, 0){
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view){
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
    }


    /*On nav drawer item click listener*/
    private class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            drawerList.setItemChecked(position, true);
            drawerLayout.closeDrawer(drawerList);
        }
    }

    /*Drawer item click swap fragment method*/
    private void selectItem(int position){
        switch(position){
            case 1:
                fm = getFragmentManager();
                PreferencesFragment pf = new PreferencesFragment();
                fm.beginTransaction()
                        .setCustomAnimations(R.animator.enter, R.animator.exit)
                        .replace(R.id.content_frame, pf)
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                fm = getFragmentManager();
                SettingsFragment sf = new SettingsFragment();
                fm.beginTransaction().replace(R.id.content_frame, sf).addToBackStack(null).commit();
                break;
            case 3:
                fm = getFragmentManager();
                HelpFragment hf = new HelpFragment();
                fm.beginTransaction().replace(R.id.content_frame, hf).addToBackStack(null).commit();
                break;
            case 4:
                fm = getFragmentManager();
                ShareFragment shf = new ShareFragment();
                fm.beginTransaction().replace(R.id.content_frame, shf).addToBackStack(null).commit();
                break;
        }
    }


    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        return super.onPrepareOptionsMenu(menu);
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



}
