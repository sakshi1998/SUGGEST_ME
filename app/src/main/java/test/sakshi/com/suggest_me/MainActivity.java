package test.sakshi.com.suggest_me;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;


import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

import test.sakshi.com.suggest_me.adapter.stadapter;
import test.sakshi.com.suggest_me.data.columnndes;
import test.sakshi.com.suggest_me.data.database;
import test.sakshi.com.suggest_me.utils.Constants;
import test.sakshi.com.suggest_me.utils.Utils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,LoaderManager.LoaderCallbacks<Cursor>{
    RecyclerView recyclerView;
    StaggeredGridLayoutManager stGridLayoutManager;
    stadapter staggeredAdapter;
    Cursor cursor;
    Cursor c;
    SQLiteDatabase db;
    CardView emptyLayout;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton Button;
    private GoogleApiClient mGoogleApiClient;
    //SQLiteDatabase db;
    private static final int CURSOR_LOADER_ID = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SQLiteDatabase.loadLibs(this);


        setContentView(R.layout.side_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.content_home);

        emptyLayout = (CardView) findViewById(R.id.empty_layout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_home);

        Button = (FloatingActionButton) findViewById(R.id.fab_add);
        //getSupportLoaderManager().initLoader(1, null, MainActivity.this);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddSituationActivity.class);
                startActivity(i);
            }
        });


        if (Utils.isGooglePlayServicesAvailable(MainActivity.this)) {
            mGoogleApiClient = TaskApplication.getGoogleApiHelper().getGoogleApiClient();
        } else {
            Snackbar.make(coordinatorLayout, R.string.warning_no_play_services, Snackbar.LENGTH_LONG).show();
        }

        stGridLayoutManager =new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        stGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(stGridLayoutManager);
        recyclerView.setHasFixedSize(true);

        staggeredAdapter = new stadapter(mGoogleApiClient, cursor, MainActivity.this);

        recyclerView.setAdapter(staggeredAdapter);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent i = new Intent(MainActivity.this, AddSituationActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
//navigation bar
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.share_text));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
         else if (id == R.id.nav_rate) {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(myAppLinkToMarket);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, " Unable to find market app", Toast.LENGTH_LONG).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        return true;
    }
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mTaskData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data

            public Cursor loadInBackground() {
                // Will implement to load data

                // COMPLETED (5) Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data

                try {
                    return getContentResolver().query(columnndes
                                    .entry.CONTENT_URI,
                            columnndes.entry.SITUATION_PROJECTION,
                            columnndes.entry.COLUMN_ACTION + "!= ?",
                            new String[]{""},
                            null);

                } catch (Exception e) {
                    Log.e("njj", "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };

    }


    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        staggeredAdapter.swapCursor(data);
        cursor = data;

    }


    public void onLoaderReset(Loader<Cursor> loader) {
        staggeredAdapter.swapCursor(null);
    }
    public void onResume() {
        super.onResume();

        getSupportLoaderManager().restartLoader(CURSOR_LOADER_ID, null, MainActivity.this);

         Cursor c = getContentResolver().query(columnndes.entry.CONTENT_URI,
                new String[]{columnndes.entry.COLUMN_ID},
                columnndes.entry.COLUMN_ACTION + "!= ?", new String[]{""}, null);
        if (c != null) {
            if (c.getCount() != 0) {
                emptyLayout.setVisibility(View.GONE);
            }
            c.close();
        }
        if (!Utils.hasLocationPermission(this)) {
            Utils.requestLocationPermission(this, Constants.LOCATION_REQUEST_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.LOCATION_REQUEST_CODE) {

            }
        }

        c.close();
    }

}
