package com.thesanmark.clients.client_384.project_1.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
//import com.android.volley.Request;
//import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import com.thesanmark.clients.client_384.project_1.Adapters.TripAdapter;
import com.thesanmark.clients.client_384.project_1.Models.TripModel;
import com.thesanmark.clients.R;
import com.thesanmark.clients.client_384.project_1.Utility.Constants;

public class TripListActivity extends BaseActivity {


    RecyclerView recyclerView;

    final String TAG = "all_trip";
    List<TripModel> modelList;

    static boolean flag = false;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        flag = true;
        initView();
    }

    void showView()
    {
        Intent intent = new Intent(TripListActivity.this, NewTripActivity.class);
        startActivity(intent);
    }
    private void initView() {
        modelList = new ArrayList<>();
//        initData(0, 10);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showView();
            }
        });
        recyclerView.setHasFixedSize(true);

//        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try  {
//                    //Your code goes here
//                    newInitData(0, 10);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();
//        new GetAllTrips().execute(new String[]{"0", "20"});


    }
//    private class GetAllTrips extends AsyncTask(String, Void, JSONArray) {
//
//        protected JSONArray doInBackground(String... urls) {
//            JSONArray array = new JSONArray();
//            OkHttpClient client = new OkHttpClient();
//            RequestBody requestBody = new MultipartBuilder()
//                    .type(MultipartBuilder.FORM) //this is what I say in my POSTman (Chrome plugin)
//                    .addFormDataPart("name", "test")
//                    .addFormDataPart("quality", "240p")
//                    .build();
//            String myUrl = Constants.server_url + Constants.all_trips + String.format("?start=%d&count=%d", urls[0], urls[1]);
//
//            Request request = new Request.Builder()
//                    .url(myUrl)
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("auth-token", Constants.str_token)
//                    .build();
//            try {
//                Response response = client.newCall(request).execute();
//                //   hidedialog();
//                String responseString = response.body().string();
//                response.body().close();
//                // do whatever you need to do with responseString
//                Log.d("result", responseString);
//                array = new JSONArray(responseString);
//
//
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//                // hidedialog();
//            }
//            return array;
//        }
//
//        protected void onPostExecute(JSONArray array) {
//            int l = array.length();
//            modelList = new ArrayList<>();
//            Log.d("size", "" + l);
//
//                try {
//                    for (int i = 0; i < l; i++) {
//                        modelList.add(TripModel.getFromJson(array.getJSONObject(i)));
//                    }
//                    recyclerView.setAdapter(new TripAdapter(TripListActivity.this, modelList));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//        }
//
//
//    }

    private class GetAllTrips extends AsyncTask<String, Void, JSONArray>
    {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showdialog();
        }

        @Override
        protected JSONArray doInBackground(String... urls) {
            JSONArray array = new JSONArray();

//            RequestBody requestBody = new MultipartBuilder()
//                    .type(MultipartBuilder.FORM) //this is what I say in my POSTman (Chrome plugin)
//                    .addFormDataPart("name", "test")
//                    .addFormDataPart("quality", "240p")
//                    .build();
            String myUrl = Constants.server_url + Constants.all_trips + String.format("?start=%s&count=%s", urls[0], urls[1]);

            Request request = new Request.Builder()
                    .url(myUrl)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("auth-token", Constants.str_token)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                //   hidedialog();
                String responseString = response.body().string();
                response.body().close();
                // do whatever you need to do with responseString
                Log.d("result", responseString);
                array = new JSONArray(responseString);


            }
            catch (Exception e) {
                e.printStackTrace();
                // hidedialog();
            }
            return array;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray)
        {
            modelList = new ArrayList<>();
            int l = jsonArray.length();

            try {
                for (int i = 0; i < l; i++) {
                    modelList.add(TripModel.getFromJson(jsonArray.getJSONObject(i)));
                }
                recyclerView.setAdapter(new TripAdapter(TripListActivity.this, modelList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Log.d(TAG, jsonArray.toString());
            hidedialog();

        }
    }
    private class DeleteTripById extends AsyncTask<String, Void, String>
    {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showdialog();
        }

        @Override
        protected String doInBackground(String... urls) {
            String result = "";

//            RequestBody requestBody = new MultipartBuilder()
//                    .type(MultipartBuilder.FORM) //this is what I say in my POSTman (Chrome plugin)
//                    .addFormDataPart("name", "test")
//                    .addFormDataPart("quality", "240p")
//                    .build();
            String myUrl = Constants.server_url + Constants.all_trips + String.format("/%s", urls[0]);

            Request request = new Request.Builder()
                    .url(myUrl)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("auth-token", Constants.str_token)
                    .delete()
                    .build();
            try {
                Response response = client.newCall(request).execute();
                //   hidedialog();
                String responseString = response.body().string();
                response.body().close();
                // do whatever you need to do with responseString
                Log.d("result", responseString);
                return "OK";


            }
            catch (Exception e) {
                e.printStackTrace();
                // hidedialog();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String string)
        {
             Log.d("tag", string);
            if (string.equals("OK"))
            {
                Toast.makeText(getApplicationContext(), "One trip was deleted", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(), "Deleting is failed", Toast.LENGTH_LONG).show();
             hidedialog();
             GetData();
        }
    }
    public void Update(int position)
    {
        Intent intent = new Intent(this, UpdateTripActivity.class);
        intent.putExtra("trip", modelList.get(position));
        startActivity(intent);
    }


    public void Delete(int position)
    {
        TripModel model  = modelList.get(position);
        Log.d("ID", model.getID());
        new DeleteTripById().execute(model.getID());
    }

    @Override
    protected void onStart() {
        super.onStart();
        GetData();
    }

    void GetData()
    {
        new GetAllTrips().execute(new String[]{"0", "20"});
    }

    private void newInitData(final int start, final int count)
    {

     //   showdialog();

    }
    /*
    private void initData(final int start, final int count) {
        final String url = Constants.server_url + Constants.all_trips;
//        Log.d("userpass", user + " " + pass);
        showdialog();
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        modelList = new ArrayList<>();
                        int l = response.length();
                        try {
                            for (int i = 0; i < l; i++) {
                                modelList.add(TripModel.getFromJson(response.getJSONObject(i)));
                            }
                            recyclerView.setAdapter(new TripAdapter(TripListActivity.this, modelList));
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
                params.put(Constants.start, "" + start);
                params.put(Constants.count, "" + count);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("auth-token", Constants.str_token);
                return headers;
            }
        };
        TripApp.getInstance().addToRequestQueue(jsonObjReq, TAG);
//        modelList.add(new TripModel("1", "2014-05-06", "02-05", "123.2324", "213423$!"));
//        modelList.add(new TripModel("1", "2014-05-06", "02-05", "123.2324", "213423$!"));
//        modelList.add(new TripModel("1", "2014-05-06", "02-05", "123.2324", "213423$!"));
//        modelList.add(new TripModel("1", "2014-05-06", "02-05", "123.2324", "213423$!"));
//        modelList.add(new TripModel("1", "2014-05-06", "02-05", "123.2324", "213423$!"));
    }
    */
}
