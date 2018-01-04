package test.sakshi.com.suggest_me.utils;

import android.content.Context;

import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.TimeFence;
import com.google.android.gms.awareness.state.HeadphoneState;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.maps.model.LatLng;

import test.sakshi.com.suggest_me.R;


public class Constants {

    public final static String ACTION_FENCE = "test.sakshi.com.action_fence";
    public final static int TASK_REQUEST_CODE = 1000;
    public final static int SITUATION_REQUEST_CODE = 1001;
    public final static int LOCATION_REQUEST_CODE =1002;
    public final static int PLACE_PICKER_REQUEST = 1003;
    public static final String ACTION_DATA_UPDATED =
            "test.sakshi.com.ACTION_DATA_UPDATED";
    public static final LatLng SOUTHWEST_INDIA_BOUND= new LatLng(7.110198,60.754470);
    public static final LatLng NORTHEAST_INDIA_BOUND= new LatLng(36.2994113,96.8162293);


    public static int getHeadPhoneStateInteger(String state, Context context) {

        if (state.equals(context.getString(R.string.headphone_plugged)))
            return HeadphoneState.PLUGGED_IN;

        if (state.equals(context.getString(R.string.headphone_unplugged)))
            return HeadphoneState.UNPLUGGED;

        return HeadphoneState.UNPLUGGED;

    }

    public static int getWeatherStateInteger(String state, Context context) {
        if (state.equals(context.getString(R.string.weather_hazy)))
            return Weather.CONDITION_HAZY;

        if (state.equals(context.getString(R.string.weather_snowy)))
            return Weather.CONDITION_SNOWY;

        if (state.equals(context.getString(R.string.weather_clear)))
            return Weather.CONDITION_CLEAR;

        if (state.equals(context.getString(R.string.weather_cloudy)))
            return Weather.CONDITION_CLOUDY;

        if (state.equals(context.getString(R.string.weather_foggy)))
            return Weather.CONDITION_FOGGY;

        if (state.equals(context.getString(R.string.weather_icy)))
            return Weather.CONDITION_ICY;

        if (state.equals(context.getString(R.string.weather_rainy)))
            return Weather.CONDITION_RAINY;

        if (state.equals(context.getString(R.string.weather_stormy)))
            return Weather.CONDITION_STORMY;

        if (state.equals(context.getString(R.string.weather_windy)))
            return Weather.CONDITION_WINDY;

        return Weather.CONDITION_HAZY;
    }

    public static int getActivityStateInteger(String state, Context context) {
        if (state.equals(context.getString(R.string.in_vehicle)))
            return DetectedActivityFence.IN_VEHICLE;

        if (state.equals(context.getString(R.string.on_bicycle)))
            return DetectedActivityFence.ON_BICYCLE;

        if (state.equals(context.getString(R.string.on_foot)))
            return DetectedActivityFence.ON_FOOT;

        if (state.equals(context.getString(R.string.running)))
            return DetectedActivityFence.RUNNING;

        if (state.equals(context.getString(R.string.still)))
            return DetectedActivityFence.STILL;

        if (state.equals(context.getString(R.string.tilting)))
            return DetectedActivityFence.TILTING;

        if (state.equals(context.getString(R.string.walking)))
            return DetectedActivityFence.WALKING;

        return DetectedActivityFence.STILL;
    }
    /*public static int getTime(String state ,Context context){
        if(state.equals("getString(R.string.time_up)"))
            return TimeFence.inInterval(0, 5);

    }*/

}
