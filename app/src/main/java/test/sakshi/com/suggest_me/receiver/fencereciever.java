package test.sakshi.com.suggest_me.receiver;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.fence.FenceState;
import com.google.android.gms.awareness.snapshot.WeatherResult;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

import test.sakshi.com.suggest_me.AddSituationActivity;
import test.sakshi.com.suggest_me.FingerprintActivity;
import test.sakshi.com.suggest_me.R;

import test.sakshi.com.suggest_me.TaskApplication;
import test.sakshi.com.suggest_me.createpair;
import test.sakshi.com.suggest_me.utils.Utils;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.pm.PackageManager.GET_ACTIVITIES;
import static android.content.pm.PackageManager.GET_PROVIDERS;
import static android.content.pm.PackageManager.GET_RECEIVERS;
import static android.content.pm.PackageManager.GET_SERVICES;

/**
 * Created by sakshi on 30-Sep-17.
 */

public class fencereciever extends BroadcastReceiver {


    GoogleApiClient mGoogleApiClient;
    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.mContext = context;
        FenceState fenceState = FenceState.extract(intent);
        mGoogleApiClient = TaskApplication.getGoogleApiHelper().getGoogleApiClient();
        /*SharedPreferences prefs = getSharedPreferences(
                DataAccessServer.PREFS_NAME, MODE_PRIVATE);*/
        if (TextUtils.equals(intent.getStringExtra("id"), fenceState.getFenceKey())) {
            if (fenceState.getCurrentState() == FenceState.TRUE) {
                final String action = intent.getStringExtra("action");
                String id = intent.getStringExtra("id");

                final Integer weatherId = intent.getIntExtra("Weather", 0);
                final String appName = intent.getStringExtra("appName");
                final String desc = intent.getStringExtra("situationDesc");
                final Integer  time1= intent.getIntExtra("time",0);
                String user="";
                user=intent.getStringExtra("useractivity");
                List<ApplicationInfo> appsList;
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (Utils.isPlayServicesAvailable(context)) {
                        if (weatherId != 0) {
                            Awareness.SnapshotApi.getWeather(mGoogleApiClient)
                                    .setResultCallback(new ResultCallback<WeatherResult>() {
                                        @Override
                                        public void onResult(@NonNull WeatherResult weatherResult) {
                                            if (weatherResult.getStatus().isSuccess()) {
                                                Weather weather = weatherResult.getWeather();
                                                if (weather.getConditions()[0] == weatherId) {
                                                    showNotification(action, appName, desc);
                                                }
                                            }
                                        }
                                    });
                        } else if (time1 != 0) {


                            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                            String mPackageName;
                            /*List<ActivityManager.RunningAppProcessInfo> processes = mActivityManager.getRunningServices(Integer.MAX_VALUE);

                            for (ActivityManager.RunningAppProcessInfo processInfo : processes) {
                                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                                    for (String activeProcess : processInfo.pkgList) {
                                        {   Log.v("jkj", activeProcess);
                                            if (action.equals(activeProcess)) {
                                                Log.v("jkj", "hjj");
                                                Intent p = new Intent(mContext, FingerprintActivity.class);
                                                p.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                mContext.startActivity(p);
                                            }
                                        }
                                        Log.v("df", action);
                                    }

                                }
                            }*/
                            int x=0;
                            List<ActivityManager.RunningServiceInfo> processes = mActivityManager.getRunningServices(Integer.MAX_VALUE);

                            for (ActivityManager.RunningServiceInfo processInfo : processes) {
                                            Log.v("dfg",processInfo.service.getPackageName());
                                            if (action.equals(processInfo.service.getPackageName())) {
                                                Log.v("jkj", "hjj");
                                                x++;
                                                Intent p = new Intent(mContext, FingerprintActivity.class);
                                                p.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                mContext.startActivity(p);
                                            }
                                        }
                                        Log.v("df", action);
                            if(x==0 && user=="Walking"){
                                String h=desc + " "+"you have walk a lot now ,you sholud take some rest";
                                showNotification(action,appName,h);
                            }
                            else if(x==0){
                                String str=desc+" "+"We suggest you not to use you phone for long time..";
                                showNotification(action,appName,str);
                                Intent p = new Intent(mContext, FingerprintActivity.class);
                                p.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                mContext.startActivity(p);
                            }




                    }

                        else {
                            Log.v("jhj","hjh");
                            showNotification(action,appName,desc);

                        }

                    }
                }
            }
        }


    }

    private void showNotification(String action,String appName,String desc)
    {

        NotificationManager notificationManager = (NotificationManager)
                mContext.getSystemService(NOTIFICATION_SERVICE);

        Intent mIntent=mContext.getPackageManager().getLaunchIntentForPackage(action);
        //mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

       ComponentName comp = new ComponentName(action,
                appName);



        if (mIntent == null) {
            mIntent=new Intent();
            mIntent.setComponent(comp);

            mIntent.setAction("android.intent.action.VIEW");
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        }
        PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), mIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);
        notificationBuilder.setContentTitle(" Open " + appName);

        notificationBuilder.setContentText(desc);
        notificationBuilder.setLights(Color.parseColor("#0086dd"), 2000, 2000);
        notificationBuilder.setSmallIcon(R.mipmap.dfdf);
        notificationBuilder.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        notificationBuilder.setContentIntent(pIntent);
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(desc));

        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
        notificationBuilder.setAutoCancel(true);

        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        //notification.defaults |= Notification.DEFAULT_SOUND;

        notificationManager.notify(0, notification);
    }


}
