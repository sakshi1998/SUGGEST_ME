package test.sakshi.com.suggest_me;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import test.sakshi.com.suggest_me.data.columnndes;
import test.sakshi.com.suggest_me.utils.Constants;

import static test.sakshi.com.suggest_me.utils.Constants.PLACE_PICKER_REQUEST;

/**
 * Created by sakshi on 27-Sep-17.
 */



public class AddSituationActivity extends AppCompatActivity{

    Button ButtonView,ButtonView1,ButtonView2;
    EditText nameEdt;

    CardView headPhoneCard, weatherCard, locationCard, activityCard, timeCard;
    TextView tvHeadphone, tvWeather, tvLocation, tvActivity, tvTime;
    String nameText, headPhoneText, weatherText, latitudeText, longitudeText, activityText, timeText, placeText;
    Long timeLongText;
    CoordinatorLayout coordinatorLayout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_situation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvTime = (TextView) findViewById(R.id.tv_time);
        tvWeather=(TextView)findViewById(R.id.tv_weather);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        tvActivity = (TextView) findViewById(R.id.tv_activity);
        locationCard = (CardView) findViewById(R.id.location_card);
        ButtonView = (Button) findViewById(R.id.head_butt);
        //ButtonView1 = (Button) findViewById(R.id.loc_id);
        nameEdt = (EditText) findViewById(R.id.name_edt);
        timeCard = (CardView) findViewById(R.id.time_date_card);

        weatherCard = (CardView) findViewById(R.id.weather_card);
        activityCard = (CardView) findViewById(R.id.user_activity_card);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        timeLongText=0L;
        nameText = headPhoneText = weatherText = latitudeText = longitudeText = activityText = timeText = placeText ="";
        ButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent g = new Intent( view.getContext(),createpair.class);
                view.getContext().startActivity(g);
            }
        });
        /*ButtonView1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent p = new Intent( AddSituationActivity.this,createpair.class);
                startActivity(p);
            }
        });*/

        final String[] weatherStates = getResources().getStringArray(R.array.weather_array);
        final String[] activityStates = getResources().getStringArray(R.array.activity_array);

        weatherCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddSituationActivity.this);

                builder.setTitle("Select Weather")
                        .setItems(R.array.weather_array, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                weatherText = weatherStates[i];
                                Log.v("dsf",weatherText);
                                tvWeather.setText(weatherText);

                            }
                        });

                builder.show();
            }
        });
        activityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddSituationActivity.this);

                builder.setTitle("Select Activity ")
                        .setItems(R.array.activity_array, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                activityText = activityStates[i];
                                tvActivity.setText(activityText);
                            }
                        });

                builder.show();
            }
        });
        locationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    LatLngBounds latLngBounds = new LatLngBounds(Constants.SOUTHWEST_INDIA_BOUND, Constants.NORTHEAST_INDIA_BOUND);
                    intentBuilder.setLatLngBounds(latLngBounds);
                    Intent intent = intentBuilder.build(AddSituationActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(AddSituationActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_location, null);

                final EditText lat = (EditText) dialogView.findViewById(R.id.latitude);
                final EditText lon = (EditText) dialogView.findViewById(R.id.longitude);

                builder.setTitle("Choose Location")
                        .setView(dialogView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                latitudeText = lat.getText().toString();
                                longitudeText = lon.getText().toString();
                                if (TextUtils.isEmpty(latitudeText) || TextUtils.isEmpty(longitudeText)) {
                                    Snackbar.make(coordinatorLayout, "Please fill in both LATITUDE and LONGITUDE", Snackbar.LENGTH_SHORT)
                                            .show();
                                } else {
                                    tvLocation.setText(latitudeText + "," + longitudeText);
                                    dialogInterface.cancel();
                                }
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                builder.show();

            }
        });

        timeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .setMinDate(new Date())
                        .build()
                        .show();
            }
        });





    }
    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            try {
                timeText = simpleDateFormat.format(date);
                tvTime.setText(timeText);
                timeLongText = date.getTime();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void onDateTimeCancel() {
        }
    };

    private void saveData() {
        String savedTime;
        nameText = nameEdt.getText().toString();

        if (TextUtils.isEmpty(nameText)) {
            Snackbar.make(coordinatorLayout, R.string.no_name_warning, Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (timeLongText == 0L) {
            savedTime = "";
        } else {
            savedTime = String.valueOf(timeLongText);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(columnndes.entry.COLUMN_NAME, nameText);
        contentValues.put(columnndes.entry.COLUMN_HEADPHONE_STATE, "");
        contentValues.put(columnndes.entry.COLUMN_WEATHER_STATE,weatherText);
        contentValues.put(columnndes.entry.COLUMN_LATITUDE, latitudeText);
        contentValues.put(columnndes.entry.COLUMN_LONGITUDE, longitudeText);
        contentValues.put(columnndes.entry.COLUMN_PLACE, "");
        contentValues.put(columnndes.entry.COLUMN_ACTIVITY,activityText);
        contentValues.put(columnndes.entry.COLUMN_TIME, savedTime);
        contentValues.put(columnndes.entry.COLUMN_ACTION, "");
        contentValues.put(columnndes.entry.COLUMN_ACTION_NAME, "");
        contentValues.put(columnndes.entry.COLUMN_CHECKED, "0");


        getContentResolver().insert(columnndes.entry.CONTENT_URI,
                contentValues);

        finish();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_create) {
            saveData();
            return true;
        }

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            final String name = place.getAddress().toString();
            final LatLng latLng = place.getLatLng();

            latitudeText = String.valueOf(latLng.latitude);
            longitudeText = String.valueOf(latLng.longitude);
            placeText = name;

            tvLocation.setText(placeText);


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
