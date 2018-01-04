package test.sakshi.com.suggest_me;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LomainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    Button _btnLogin;
    EditText _txtEmail, _txtPass;
    Cursor cursor;
    CardView login;
    int x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lomain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        login = (CardView) findViewById(R.id.login_card);
        _txtEmail = (EditText)findViewById(R.id.txtEmail);
        _txtPass = (EditText)findViewById(R.id.txtPass);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = _txtEmail.getText().toString();
                String pass = _txtPass.getText().toString();
                if( TextUtils.isEmpty(email) ||TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(), "incorrect email/ password", Toast.LENGTH_LONG).show();
                }
                else {
                    cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_3 + "=?", new String[]{email, pass});
                    if (cursor != null) {

                        if (cursor.getCount() > 0) {
                            cursor.moveToNext();
                            Intent intent = new Intent(LomainActivity.this,MainActivity.class);
                            startActivity(intent);
                        } else {


                            Toast.makeText(getApplicationContext(), "incorrect email/ password", Toast.LENGTH_LONG).show();

                        }
                    }
                }
            }
        });
    }
}
