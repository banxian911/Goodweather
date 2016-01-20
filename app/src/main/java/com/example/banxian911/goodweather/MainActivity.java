package com.example.banxian911.goodweather;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.banxian911.Util.BackHandledFragment;

public class MainActivity extends AppCompatActivity implements BackHandledFragment.BackHandlerInterface {

    private DrawerLayout mDrawelayout;
    private NavigationView mNavigation;
    private ActionBarDrawerToggle mDrawerToggle;
    private BackHandledFragment selectedFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationviewlayout);
        mDrawelayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigation = (NavigationView) findViewById(R.id.id_nv_menu);

        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        switchToWeather();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawelayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawelayout.setDrawerListener(mDrawerToggle);
        setupDrawerContent(mNavigation);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()){

                            case R.id.navigation_item_weather:
                                switchToWeather();

                                break;
                            case R.id.navigation_item_setting:
                                switchTosetting();

                                break;
                            case R.id.navigation_item_about:
                                switchToAbout();

                                break;

                        }
                        menuItem.setChecked(true);
                        mDrawelayout.closeDrawers();
                        return true;
                    }
                }
        );

    }

    private void switchToWeather(){

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_content, new WeatherFragment())
                .commit();
        toolbar.setVisibility(View.GONE);
    }
    private void switchTosetting(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_content,new SettingFragment())
                .commit();
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle(R.string.Settings);
    }
    private void switchToAbout(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_content,new AboutFragment())
                .commit();
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle(R.string.About);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
    private long exitTime =0;
    public void doExitApp(){
        if((System.currentTimeMillis() - exitTime) > 2000){

            Toast.makeText(this,R.string.press_again_exit_app,Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }else {
            finish();
        }

    }

    @Override
    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
        this.selectedFragment = backHandledFragment;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (selectedFragment == null || !selectedFragment.onBackPressed()){
            if (mDrawelayout.isDrawerOpen(Gravity.LEFT)){
                mDrawelayout.closeDrawer(Gravity.LEFT);
            }else{
                doExitApp();
            }
        }
    }
}
