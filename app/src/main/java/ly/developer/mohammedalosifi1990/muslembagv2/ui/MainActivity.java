package ly.developer.mohammedalosifi1990.muslembagv2.ui;

import android.app.SearchManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
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
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;


import ly.developer.mohammedalosifi1990.muslembagv2.R;
import ly.developer.mohammedalosifi1990.muslembagv2.base.BaseActivity;
import ly.developer.mohammedalosifi1990.muslembagv2.ui.PrayTimes.PrayTimes_;
import ly.developer.mohammedalosifi1990.muslembagv2.ui.QuranListen.QuranListenFragment;
import ly.developer.mohammedalosifi1990.muslembagv2.ui.QuranListen.QuranListenFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


     SearchManager searchManager;
    SearchView searchView;
    @ViewById
    Toolbar toolbar;
    @ViewById
    DrawerLayout drawer;
    @ViewById
    NavigationView navigationView;

//    @ViewById
//    TextView tvAddress;
    @AfterViews
    protected void afterView() {
        setSupportActionBar(toolbar);

        if (getIntent().getExtras().getInt("openType")==1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, new PrayTimes_()).commit();;
        }else if (getIntent().getExtras().getInt("openType")==2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, new QuranListenFragment_()).commit();;
        }else {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fl, new QuranListenFragment_()).commit();;
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        QuranListenFragment quranListenFragment = (QuranListenFragment) getSupportFragmentManager().findFragmentById(R.id.fragQuranListen);
     }

    @Override
    public void onBackPressed() {
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
        navigationView.setNavigationItemSelectedListener(this);
        searchManager = (SearchManager) getSystemService(MainActivity.this.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setVisibility(View.GONE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setClickable(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showToast("aaaaaaaaaaa"+newText,"s");
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

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

        if (id == R.id.nav_camera) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, new QuranListenFragment_()).commit();;

        } else if (id == R.id.nav_gallery) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, new PrayTimes_()).commit();;

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

         drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
