package com.thesanmark.clients.client_384.project_1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.android.volley.Request;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;

import org.json.JSONObject;

import com.thesanmark.clients.R;
import com.thesanmark.clients.client_384.project_1.Utility.Constants;

public class MainActivity extends BaseActivity {

    private String url;
    String TAG = "json_obj_req";
    EditText edit_username, edit_password;
    Button button1, button2;
    String user, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        edit_username = (EditText) findViewById(R.id.editText);
        edit_password = (EditText) findViewById(R.id.editText2);
        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                login();
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            //Your code goes here
                            newLogIn();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();

                Intent intent = new Intent(MainActivity.this, TripListActivity.class);
//                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    boolean validate()
    {
        user = edit_username.getText().toString().trim();
        pass = edit_password.getText().toString().trim();
        if (user.equals("") || pass.equals("")) return false;
        return true;

    }
    private void login()
    {
        if (!validate()) return;
        url = Constants.server_url + Constants.login;
        Log.d("userpass", user + " " + pass);
        showdialog();
/*

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("tag", "ok");
                            JSONObject jsonObject = response.getJSONObject("user");
                            Constants.userID = jsonObject.getString(Constants.ID);
                            Constants.str_username = jsonObject.getString(Constants.username);
                            Constants.str_firstname = jsonObject.getString(Constants.first_name);
                            Constants.str_lastname = jsonObject.getString(Constants.last_name);
                            Constants.str_email = jsonObject.getString(Constants.email);
                            Constants.str_token = response.getString(Constants.auth_token);
                            Intent intent = new Intent(MainActivity.this, TripListActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                        Log.d(TAG, response.toString());
                        hidedialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.toString());
                hidedialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.username, user);
                params.put(Constants.password, pass);

                return params;
            }
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                TripApp.getInstance().addSessionCookie(headers);
                return headers;
            }
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                // since we don't know which of the two underlying network vehicles
                // will Volley use, we have to handle and store session cookies manually
                TripApp.getInstance().checkSessionCookie(response.headers);

                return super.parseNetworkResponse(response);
            }
        };
/*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("tag", response);
                hidedialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("error", error.getMessage());
                hidedialog();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.username, user);
                params.put(Constants.password, pass);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                return headers;
            }
        }
        ;
        TripApp.getInstance().addToRequestQueue(jsonObjReq, TAG);*/
    }

    private void newLogIn()
    {
        url = Constants.server_url + Constants.login;
        OkHttpClient client = new OkHttpClient();
        if (!validate())
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "username or pasword is empty!", Toast.LENGTH_LONG).show();
                }
            });

            return;
        }
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM) //this is what I say in my POSTman (Chrome plugin)
                .addFormDataPart("username", user)
                .addFormDataPart("password", pass)
                .build();
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request .Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            String responseString = response.body().string();

            response.body().close();
            // do whatever you need to do with responseString
            Log.d("tag", responseString);
            JSONObject responseObject = new JSONObject(responseString);
            JSONObject jsonObject = responseObject.getJSONObject("user");
            Constants.userID = jsonObject.getInt(Constants.ID) + "";
            Constants.str_username = jsonObject.getString(Constants.username);
            Constants.str_firstname = jsonObject.getString(Constants.first_name);
            Constants.str_lastname = jsonObject.getString(Constants.last_name);
            Constants.str_email = jsonObject.getString(Constants.email);
            Constants.str_token = responseObject.getString(Constants.auth_token);
            Log.d("str-token", Constants.str_token);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                }
            });

            Intent intent = new Intent(MainActivity.this, TripListActivity.class);
            startActivity(intent);
            finish();
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("error", e.toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "LogIn Error", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
