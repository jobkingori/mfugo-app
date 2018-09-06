package com.example.job.m_fugo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.job.m_fugo.Activity.Login;
import com.example.job.m_fugo.R;
import com.example.job.m_fugo.fragments.HealthHistory;
import com.example.job.m_fugo.fragments.VetsHome;
import com.example.job.m_fugo.vetsFragment.BookedDetails;
import com.example.job.m_fugo.vetsFragment.FragmentVetBookList;
import com.example.job.m_fugo.vetsFragment.HomeVet;
import com.example.job.m_fugo.vetsFragment.Update;

import static com.example.job.m_fugo.Activity.Login.MyPREF;

public class VetsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String book_id,farmer_name,farmer_phone,image_sign,description;

    public static Double lati,longi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vets);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_vetss, new HomeVet(), getString(R.string.app_name));
        ft.commit();

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
        getMenuInflater().inflate(R.menu.vets, menu);
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

        if (id == R.id.check_bookings) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_vetss, new FragmentVetBookList(), getString(R.string.app_name));
            ft.commit();

        } else if (id == R.id.updates) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_vetss, new Update(), getString(R.string.app_name));
            ft.commit();
        }
        else if (id == R.id.vetHome) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_vetss, new HomeVet(), getString(R.string.app_name));
            ft.commit();
        }
        else if (id == R.id.nav_logout1) {
            SharedPreferences.Editor editor= getSharedPreferences(MyPREF,MODE_PRIVATE).edit();
            editor.clear();
            editor.commit();

            Intent i = new Intent(getApplicationContext(),Login.class);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //go to view more details
    public  void goToViewMore(String id,String name,String phone, String image, String descriptionn, Double latii, Double longii){

        book_id=id;
        farmer_name=name;
        farmer_phone=phone;
        image_sign=image;
        description=descriptionn;

        lati=latii;
        longi=longii;

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_vetss, new BookedDetails(), getString(R.string.app_name));
        ft.commit();
    }

}
