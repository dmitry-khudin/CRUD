package com.thesanmark.clients.client_384.project_1.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONObject;

import java.util.Calendar;

import com.thesanmark.clients.R;
import com.thesanmark.clients.client_384.project_1.Utility.Constants;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class NewTripActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    EditText editText1, editText2, editText3, editText4, editText5,
            editText6, editText7, editText8, editText9, editText10, editText11, editText12, editText13;
    final String TIMEPICKER = "timepickerdialog";
    final String DATEPICKER = "datepickerdialog";

    Button button;
    String start_date, start_time, end_date, end_time, start_latitude, start_longitude,
            start_octom, client, purpose, end_latitude, end_longitude, end_octom, total_kilometers, total_time;
    int time_flag, date_flag;
    private String provider;
    private LocationManager locationManager;
    GoogleApiClient mGoogleApiClient;
    Location mylocation;
    GPSTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        if (mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }

        initiView();


    }

    @Override
    protected void onStart() {
        super.onStart();
        tracker = new GPSTracker(this);
        if (!tracker.canGetLocation()) tracker.showSettingsAlert();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }
                else
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }

            }
            else
            {
                buildlocation();
            }
        }
        else
        {
            buildlocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Toast.makeText(this, "asdfasdfasdf", Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the

                    // contacts-related task you need to do.

                    tracker = new GPSTracker(this);

                    // Check if GPS enabled
                    if (tracker.canGetLocation()) {

                        double latitude = tracker.getLatitude();
                        double longitude = tracker.getLongitude();

                        // \n is for new line
                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    }
                    else {
                        // Can't get location.
                        // GPS or network is not enabled.
                        // Ask user to enable GPS/network in settings.
                        tracker.showSettingsAlert();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(this, "You need to grant permission", Toast.LENGTH_SHORT).show();
                }
              //  return;
            }
        }
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "ammmmmmmm", Toast.LENGTH_SHORT).show();

            return;
        }
        //    locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
        buildlocation();


    }


    void buildlocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Toast.makeText(this, "location disable", Toast.LENGTH_LONG).show();
                return;
            }
            else
                mylocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        else
            mylocation = tracker.getLocation();
        if(mylocation == null) {
            editText3.setText(String.format("%.3f", 0.0));
            editText4.setText(String.format("%.3f", 0.0));
            return;
        }
        final Double latitude = mylocation.getLatitude();
        final Double longitude = mylocation.getLongitude();
        Toast.makeText(this, " " + latitude + " " + longitude, Toast.LENGTH_LONG).show();
        editText3.setText(String.format("%.3f", latitude));
        editText4.setText(String.format("%.3f", longitude));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }

    private void initiView() {
        editText1 = (EditText) findViewById(R.id.editText3);
        editText2 = (EditText) findViewById(R.id.editText4);
        editText3 = (EditText) findViewById(R.id.editText5);
        editText4 = (EditText) findViewById(R.id.editText18);
        editText5 = (EditText) findViewById(R.id.editText6);
        editText1.setKeyListener(null);
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_flag = 1;
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        NewTripActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                );
                tpd.setThemeDark(false);
                tpd.vibrate(true);
                tpd.dismissOnPause(true);
                tpd.enableMinutes(true);
                tpd.enableSeconds(false);
                tpd.setTitle("Start Time");
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was canceled");
                    }
                });
                tpd.show(getFragmentManager(), TIMEPICKER);
            }
        });
        editText2.setKeyListener(null);
        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        NewTripActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setThemeDark(false);
                dpd.vibrate(true);
                dpd.dismissOnPause(true);
                dpd.showYearPickerFirst(true);
                dpd.setTitle("Start Date");
                dpd.show(getFragmentManager(), DATEPICKER);
                date_flag = 1;
            }
        });
        button = (Button) findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PostTask().execute();
            }
        });
        editText3.setKeyListener(null);
        editText4.setKeyListener(null);

    }

    private void newTripPost()
    {
        start_date = editText1.getText().toString().trim();
        start_time = editText2.getText().toString().trim();
        start_latitude = editText3.getText().toString().trim();

        start_longitude = editText4.getText().toString().trim();
        start_octom = editText5.getText().toString().trim();

        String url = Constants.server_url + Constants.all_trips;
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM) //this is what I say in my POSTman (Chrome plugin)
                .addFormDataPart(Constants.start_time, start_time)
                .addFormDataPart(Constants.start_date, start_date)
                .addFormDataPart(Constants.start_longitude, start_longitude)
                .addFormDataPart(Constants.start_latitude, start_latitude)
                .addFormDataPart(Constants.start_odometer, start_octom)
                .build();
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request .Builder()
                .url(url)

                .addHeader(Constants.auth_token, Constants.str_token)
                .post(requestBody)
                .build();
        try {
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            String responseString = response.body().string();

            response.body().close();
            // do whatever you need to do with responseString
            Log.d("tag", responseString);

            JSONObject responseObject = new JSONObject(responseString);
            JSONObject jsonObject = responseObject.getJSONObject("trip");
            Constants.tripID = jsonObject.getInt("id")  + "";

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }
            });
            Log.d("str-token", Constants.str_token);
//            Intent intent = new Intent(NewTripActivity.this, TripListActivity.class);
//            startActivity(intent);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("error", e.toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag(TIMEPICKER);
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag(DATEPICKER);

        if(tpd != null) tpd.setOnTimeSetListener(this);
        if(dpd != null) dpd.setOnDateSetListener(this);
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
        if (date_flag == 1)       editText1.setText(date);
        else if (date_flag == 2) editText8.setText(date);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String secondString = second < 10 ? "0"+second : ""+second;
        String time = "You picked the following time: "+hourString+"h"+minuteString+"m"+secondString+"s";
        time = String.format("%02d:%02d", hourOfDay, minute);
        if (time_flag == 1) editText2.setText(time);
        else if (time_flag == 2) editText9.setText(time);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    class PostTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            showdialog();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            hidedialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newTripPost();
            return null;
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.start:

                new PostTask().execute();
//                Toast.makeText(this, "开始游戏", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
