package com.letsgo.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.letsgo.R;
import com.letsgo.model.Event;
import com.letsgo.model.User;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Communicator {

//    TODO decide if backStack should be more than one step

    UserDao userDataSource;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userDataSource = new UserDataSource(this);
        ((UserDataSource)userDataSource).open();

        currentUser = getIntent().getParcelableExtra("user");
        keepUserid(currentUser);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//      Get user email and username
        View headView = navigationView.getHeaderView(0);
//        String email = getIntent().getStringExtra("userMail");
        ((TextView) headView.findViewById(R.id.nav_header_email)).setText(currentUser.getEmail());
        ((TextView) headView.findViewById(R.id.nav_header_username)).setText(currentUser.getUsername());
//        ((TextView) headView.findViewById(R.id.nav_header_email)).setText(email);
//        ((TextView) headView.findViewById(R.id.nav_header_username)).setText(currentUser.getUsername());

//        Load initial fragment
        if (savedInstanceState == null){
            Fragment allEvents = new FragmentAllEvents();
            loadInitialFrag(allEvents);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        View ticktetContainer = findViewById(R.id.land_frag_ticket);
        if (ticktetContainer != null && ticktetContainer.getVisibility() == View.VISIBLE){
            ticktetContainer.setVisibility(View.GONE);
            findViewById(R.id.frag_container).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        if (id == R.id.options_menu_main_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_all) {
            Fragment allEvents = new FragmentAllEvents();
            replaceFrag(allEvents);

        } else if (id == R.id.nav_watchlist) {
            Fragment watchlist = new FragmentWatchlist();
            replaceFrag(watchlist);

        } else if (id == R.id.nav_upcomming) {
            Fragment upcomming = new FragmentUpcommingEvents();
            replaceFrag(upcomming);

        } else if (id == R.id.nav_past) {
            Fragment pastEvents = new FragmentPastEvents();
            replaceFrag(pastEvents);

        } else if (id == R.id.nav_adv_search) {
            Fragment advSearch = new FragmentAdvancedSearch();
            replaceFrag(advSearch);

        } else if (id == R.id.nav_edit_profile) {
            Fragment edtProfile = new FragmentEditProfile();
            replaceFrag(edtProfile);

        } else if (id == R.id.nav_tickets) {
            Fragment tickets = new FragmentTickets();
            replaceFrag(tickets);

        } else if (id == R.id.nav_logout) {
//            Clear shared pref file
            SharedPreferences log = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            log.edit().clear().commit();
            SharedPreferences userId = getPreferences(MODE_PRIVATE);
            userId.edit().clear().commit();

            Intent logoutIntent = new Intent(this,ActivityLogin.class);
            startActivity(logoutIntent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadInitialFrag(Fragment fr){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frag_container, fr);
        ft.commit();
    }

    private void replaceFrag(Fragment fr){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_container,fr);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onResume() {
        ((UserDataSource)userDataSource).open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        ((UserDataSource)userDataSource).close();
        super.onPause();
    }

    private void keepUserid(User user){
//        TODO save user info (mail)
        SharedPreferences userId = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = userId.edit();
        editor.putLong("user_id", user.getId());
        editor.commit();
    }

    @Override
    protected void onStop() {
        SharedPreferences userId = getPreferences(MODE_PRIVATE);
        userId.edit().clear().commit();
        super.onStop();
    }

    @Override
    public void sendEvent(AbstractFragment receiver,Event event,boolean isFav) {
//        LoadSingleEventFragment.sendEventToSingleEventFragment(ActivityMain.this.getSupportFragmentManager(),event,fragment,isFav);
        receiver.getEvent(event, isFav);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        View ticktetContainer = findViewById(R.id.land_frag_ticket);
        if (ticktetContainer != null && ticktetContainer.getVisibility() == View.VISIBLE){
            tr.replace(R.id.land_frag_ticket, receiver);
        }
        else if (findViewById(R.id.land_frag_container) != null)
            tr.replace(R.id.land_frag_container, receiver);
        else
            tr.replace(R.id.frag_container, receiver);
        tr.addToBackStack(null);
        tr.commit();
    }

    @Override
    public void sendSearchCriteria(AbstractFragment receiver, String eventName, String eventType,
                                   String eventLocation, String afterDate, String beforeDate) {
        receiver.getSearchCriteria(eventName,eventType,eventLocation,afterDate,beforeDate);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        View ticktetContainer = findViewById(R.id.land_frag_ticket);
        if (ticktetContainer != null && ticktetContainer.getVisibility() == View.VISIBLE){
            tr.replace(R.id.land_frag_ticket, receiver);
        }
        else if (findViewById(R.id.land_frag_container) != null)
            tr.replace(R.id.land_frag_container, receiver);
        else
            tr.replace(R.id.frag_container, receiver);
        tr.addToBackStack(null);
        tr.commit();
    }
}