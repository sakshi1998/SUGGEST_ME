package test.sakshi.com.suggest_me;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button  _btnlogin;
    CardView btnreg;
    EditText _txtfname, _txtpass, _txtemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        openHelper=new DatabaseHelper(this);
        btnreg=(CardView)findViewById(R.id.register);
        _txtfname=(EditText)findViewById(R.id.txtfname);
        _txtpass=(EditText)findViewById(R.id.txtpass);
        _txtemail=(EditText)findViewById(R.id.txtemail);
        _btnlogin=(Button)findViewById(R.id.btnlogin);
        btnreg.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v)
            {
                db=openHelper.getWritableDatabase();
                String fname=_txtfname.getText().toString();
                String pass=_txtpass.getText().toString();
                String email=_txtemail.getText().toString();
                if(!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(email) ) {
                    insertdata(fname, pass, email);
                    Toast.makeText(getApplicationContext(), "register successfully", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                }
            }
        });
        _btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LomainActivity.class);
                startActivity(intent);

            }
        });
    }

    public void insertdata(String fname, String pass, String email)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2, fname);
        contentValues.put(DatabaseHelper.COL_3, pass);
        contentValues.put(DatabaseHelper.COL_4, email);
        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);

    }
}
