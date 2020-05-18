package com.example.localhost;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username, password, email;
    private TextView existinguser;
    private Button register;
    private String name, pword, emailid;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SavedPref.getInstance(this).LoggedIn()) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }

        register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(this);
        existinguser = (TextView) findViewById(R.id.Existinguser);
        existinguser.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if (v == register) {
            initedittext();
        } else if (v == existinguser) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
        }

    }

    private void initedittext() {

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        progressDialog = new ProgressDialog(this);
        name = username.getText().toString();
        pword = password.getText().toString();
        emailid = email.getText().toString();


        createRequest();

    }

    private void createRequest() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(MainActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    Log.e("ERRORMESSAGE1", "" + jsonObject.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ERRORMESSAGE2", "" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", name);
                params.put("password", pword);
                params.put("email", emailid);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
