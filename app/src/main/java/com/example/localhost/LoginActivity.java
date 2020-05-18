package com.example.localhost;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username, password;
    private Button login;
    private String uname, pword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SavedPref.getInstance(this).LoggedIn()) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == login) {
            initEditText();
        }
    }

    private void initEditText() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        uname = username.getText().toString();
        pword = password.getText().toString();
        StartRequesting();

    }

    private void StartRequesting() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {
                        SavedPref.getInstance(getApplicationContext()).userlogin(
                                jsonObject.getInt("id"),
                                jsonObject.getString("username"),
                                jsonObject.getString("email")
                        );
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid User name or password!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", uname);
                params.put("password", pword);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
