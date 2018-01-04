package test.sakshi.com.suggest_me;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import test.sakshi.com.suggest_me.utils.GoogleApiHelper;


public class TaskApplication extends Application {
    private GoogleApiHelper googleApiHelper;
    private static TaskApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        googleApiHelper = new GoogleApiHelper(getApplicationContext());
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static synchronized TaskApplication getInstance() {
        return mInstance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance() {
        return this.googleApiHelper;
    }
    public static GoogleApiHelper getGoogleApiHelper() {
        return getInstance().getGoogleApiHelperInstance();
    }
}