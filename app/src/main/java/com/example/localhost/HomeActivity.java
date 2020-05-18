package com.example.localhost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private TextView name, mailid, ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!SavedPref.getInstance(this).LoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        initmethod();

    }

    private void initmethod() {
        name = (TextView) findViewById(R.id.name);
        mailid = (TextView) findViewById(R.id.mail);
        name.setText(SavedPref.getInstance(this).username());
        mailid.setText(SavedPref.getInstance(this).mailid());

    }

}
