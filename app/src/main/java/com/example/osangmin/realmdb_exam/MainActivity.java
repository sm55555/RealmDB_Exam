package com.example.osangmin.realmdb_exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.RealmModel;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<Realm> {
    private Realm mRealm;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mNewPassword;
    private TextView mResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRealm = Realm.getDefaultInstance();
        mEmail = findViewById(R.id.email_edit);
        mPassword = findViewById(R.id.password_edit);
        mNewPassword = findViewById(R.id.new_password_edit);
        mResultText = findViewById(R.id.result_text);


        mRealm = Realm.getDefaultInstance();
        mRealm.addChangeListener(this);

        showResult();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    public void singIn(View view) {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        User user = mRealm.where(User.class)
                .equalTo("email", email)
                .equalTo("password", password)
                .findFirst();

        if(user != null){
            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();
        }

    }

    public void singUp(View view) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                if (realm.where(User.class).equalTo("email", mEmail.getText().toString()).count() > 0) {
                    realm.cancelTransaction();
                }

                User user = realm.createObject(User.class);
                user.setEmail(mEmail.getText().toString());
                user.setPassword(mPassword.getText().toString());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "성공", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(MainActivity.this, "실패", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updatepassword(View view) {
       final User user = mRealm.where(User.class)
                .equalTo("email",mEmail.getText().toString())
                .equalTo("password", mPassword.getText().toString())
                .findFirst();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                user.setPassword(mNewPassword.getText().toString());
            }
        });
    }

    public void deleteAccount(View view) {
        final RealmResults<User> results = mRealm.where(User.class)
                .equalTo("email",mEmail.getText().toString())
                .equalTo("password", mPassword.getText().toString())
                .findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });

    }


    public void showResult(){
        RealmResults<User> userList = mRealm.where(User.class).findAll();
        mResultText.setText(userList.toString());
    }


    @Override
    public void onChange(Realm realm) {
        showResult();
    }
}
