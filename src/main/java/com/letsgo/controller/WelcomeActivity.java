package com.letsgo.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.letsgo.R;
import com.letsgo.model.DBAdapter;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBAdapter dbAdapter;

    String username;

    String userEmail;

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        manager = getFragmentManager();

        dbAdapter = new DBAdapter(this);

        userEmail = getIntent().getStringExtra("userMail");
        if (userEmail != null)
            username = dbAdapter.getUsername(userEmail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView usernameDrawer = (TextView) ((ViewGroup) navigationView.getHeaderView(0)).getChildAt(1);
        TextView userMailDrawer = (TextView) ((ViewGroup) navigationView.getHeaderView(0)).getChildAt(2);

        usernameDrawer.setText(username);
        userMailDrawer.setText(getIntent().getStringExtra("userMail"));

        showFragmentAllEvents();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_watchlist) {

        }
        else if (id == R.id.nav_upcomming) {

        }
        else if (id == R.id.nav_search) {
            showSearchEventFragment();
        }
        else if (id == R.id.nav_past) {

        }
        else if (id == R.id.nav_edit_profile) {
            showUSerProfileFragment();
        }
        else if (id == R.id.nav_all) {
            showFragmentAllEvents();
        }
        else if (id == R.id.nav_logout) {

            Intent i = new Intent(this.getApplicationContext(), LoginActivity.class);
            startActivity (i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showUSerProfileFragment(){
        UserProfileFragment uf = new UserProfileFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container,uf,"userProfileFragment");
        transaction.commit();
    }

    private void showSearchEventFragment(){
        SearchFragment sf = new SearchFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container,sf,"eventSearchFragment");
        transaction.commit();
    }

    private void showFragmentAllEvents(){
        ListAllEventsFragment allEventsFragment  = new ListAllEventsFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container, allEventsFragment,"allEventsFragment");
        transaction.commit();
    }

    private void showFragmentEditPRofile(){

    }

    private void removeFragmet(String fragmentTag){
        Fragment fr = manager.findFragmentByTag(fragmentTag);
        FragmentTransaction tr = manager.beginTransaction();
        if (fr != null) {
            tr.remove(fr);
            tr.commit();
        }

    }

    public void select_event(View view) {
        EventFragment ef = new EventFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container,ef,"eventFragment");
        transaction.commit();
    }

    public void goToBuyFragment(View view) {
        BuyTicketFragment buy = new BuyTicketFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container,buy,"buyTicket");
        transaction.commit();
    }
}
