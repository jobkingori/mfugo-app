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

import com.example.job.m_fugo.R;
import com.example.job.m_fugo.fragments.AddCowFragment;
import com.example.job.m_fugo.fragments.AddHealthRecord;
import com.example.job.m_fugo.fragments.AddMilkRecord;
import com.example.job.m_fugo.fragments.Book;
import com.example.job.m_fugo.fragments.Book_Vet;
import com.example.job.m_fugo.fragments.FarmersHome;
import com.example.job.m_fugo.fragments.Health;
import com.example.job.m_fugo.fragments.HealthHistory;
import com.example.job.m_fugo.fragments.MilkHistory;
import com.example.job.m_fugo.fragments.MilkingCows;
import com.example.job.m_fugo.fragments.Trending;
import com.example.job.m_fugo.fragments.UserHome;

import static com.example.job.m_fugo.Activity.Login.MyPREF;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

public static   String hold_vet_id, cow_id,cow_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, new UserHome(), getString(R.string.app_name));
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.news) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, new Trending(), getString(R.string.app_name));
            ft.commit();

        }
        else if (id == R.id.userHome) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, new UserHome(), getString(R.string.app_name));
            ft.commit();

        }
        else if (id == R.id.cow) {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, new AddCowFragment(), getString(R.string.app_name));
            ft.commit();

        } else if (id == R.id.milk) {

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, new MilkingCows(), getString(R.string.app_name));
            ft.commit();

        }
        else if (id == R.id.nav_logout) {
            SharedPreferences.Editor editor= getSharedPreferences(MyPREF,MODE_PRIVATE).edit();
            editor.clear();
            editor.commit();

            Intent i = new Intent(getApplicationContext(),Login.class);
            startActivity(i);
            finish();

        }
        else if (id == R.id.health) {

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, new Health(), getString(R.string.app_name));
            ft.commit();


        } else if (id == R.id.vet) {

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, new Book_Vet(), getString(R.string.app_name));
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //go to
    public  void book(String vet_id){
        hold_vet_id=vet_id;
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, new Book(), getString(R.string.app_name));
        ft.commit();
    }

    //go to add milk record

    public  void addMilkRecord(String id,String cow){
        cow_id=id;
        cow_name=cow;
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, new AddMilkRecord(), getString(R.string.app_name));
        ft.commit();
    }

    //go to add milk record

    public  void milkHistory(String id,String cow){
        cow_id=id;
        cow_name=cow;
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, new MilkHistory(), getString(R.string.app_name));
        ft.commit();
    }

//go to add health record
    public  void addHealthRecord(String id,String cow){
        cow_id=id;
        cow_name=cow;
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, new AddHealthRecord(), getString(R.string.app_name));
        ft.commit();
    }
    //go to get health record
    public  void getHealthHistory(String id,String cow){
        cow_id=id;
        cow_name=cow;
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, new HealthHistory(), getString(R.string.app_name));
        ft.commit();
    }



}
