package test.sakshi.com.suggest_me;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import test.sakshi.com.suggest_me.data.columnndes;
import test.sakshi.com.suggest_me.utils.Constants;
import test.sakshi.com.suggest_me.utils.PassAppData;
import test.sakshi.com.suggest_me.utils.PassSituationData;

/**
 * Created by sakshi on 30-Sep-17.
 */

public class createpair extends AppCompatActivity implements PassSituationData, PassAppData{
    ViewPager viewPager;
    TabLayout tabLayout;
    Integer id;
    String nameText = "";
    String appName = "", packageName = "";
    CoordinatorLayout coordinatorLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_pair);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_pair);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Action(), "ACTION");
        adapter.addFragment(new Situation(), "SITUATION");
        viewPager.setAdapter(adapter);
    }
    public void onDataPass(Integer id,String nameText) {
        this.id = id;
        this.nameText = nameText;
    }

    @Override
    public void onAppDataPass(String appName, String packageName) {
        this.appName = appName;
        this.packageName = packageName;
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private  List<Fragment> mFragmentList = new ArrayList<>();
        private  List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager frag) {
            super(frag);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public static class Action extends Fragment {

        FloatingActionButton addAppAction;
        ImageView appIcon;
        PassAppData passAppData;

        public Action() {

        }

        @Override
        public void onAttach(Activity a) {
            super.onAttach(a);
            try {
                passAppData = (PassAppData) a;
            } catch (ClassCastException e) {
                throw new ClassCastException(a.toString() + " ");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            passAppData = null;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.fragment_action, container, false);
            initViews(rootView);

            addAppAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getContext(), PickTaskActivity.class);
                    startActivityForResult(i, Constants.TASK_REQUEST_CODE);
                }
            });
            return rootView;
        }

        private void initViews(View v) {
            addAppAction = (FloatingActionButton) v.findViewById(R.id.fab);
            appIcon = (ImageView) v.findViewById(R.id.image_app);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == Constants.TASK_REQUEST_CODE) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    byte[] b = bundle.getByteArray("appIcon");
                    Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                    appIcon.setImageBitmap(bmp);
                    passAppData.onAppDataPass(bundle.getString("appName"), bundle.getString("appPackage"));
                }
            }
        }
    }

    public static class Situation extends Fragment {

        private FloatingActionButton floatingActionButton;
        private TextView situationName;
        PassSituationData dataListener;


        public Situation() {
            // Required empty public constructor
        }


        @Override
        public void onAttach(Activity a) {
            super.onAttach(a);
            try {
                dataListener = (PassSituationData) a;
            } catch (ClassCastException e) {
                throw new ClassCastException(a.toString() + " must implement PassSituationData");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            dataListener = null;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.fragment_situation, container, false);
            initViews(rootView);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getContext(), SituationListActivity.class);
                    startActivityForResult(i, Constants.SITUATION_REQUEST_CODE);
                }
            });
            return rootView;
        }

        private void initViews(View v) {
            floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fab_situation);
            situationName = (TextView) v.findViewById(R.id.situation_name);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == Constants.SITUATION_REQUEST_CODE) {
                if (data != null) {
                    Integer id = data.getIntExtra("situationId",0);
                    String nameText = data.getStringExtra("situationName");
                    dataListener.onDataPass(id,nameText);
                    situationName.setText(nameText);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            saveData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    private void saveData() {
        int x=0;
        int y=0;
        if(TextUtils.isEmpty(appName))
        {
            Snackbar.make(coordinatorLayout,"Please select the APP to proceed",Snackbar.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(nameText))
        {
            Snackbar.make(coordinatorLayout,"Please select the SITUATION to proceed",Snackbar.LENGTH_SHORT).show();
            return;
        }

            ContentValues contentValues = new ContentValues();
            contentValues.put(columnndes.entry.COLUMN_ACTION, packageName);
            contentValues.put(columnndes.entry.COLUMN_ACTION_NAME, appName);

            getContentResolver().update(columnndes.entry.CONTENT_URI, contentValues,
                    columnndes.entry.COLUMN_ID + "= ?", new String[]{String.valueOf(id)});

            getContentResolver().notifyChange(columnndes.entry.CONTENT_URI, null, false);

            finish();


    }

}
