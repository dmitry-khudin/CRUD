package com.thesanmark.clients.client_384.project_1.Activities;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.thesanmark.clients.client_384.project_1.Models.TripModel;
import com.thesanmark.clients.R;
import com.thesanmark.clients.client_384.project_1.Utility.Constants;

public class UpdateTripActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    EditText editText1, editText2, editText3, editText4, editText5,
            editText6, editText7, editText8, editText9, editText10, editText11, editText12, editText13, editText14;
    final String TIMEPICKER = "timepickerdialog";
    final String DATEPICKER = "datepickerdialog";

    Button button;
    String start_date, start_time, end_date, end_time, start_latitude, start_longitude,
            start_octom, client, purpose, end_latitude, end_longitude, end_octom, total_kilometers, total_time;
    int time_flag , date_flag;
    TripModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip);
        Bundle bundle = getIntent().getExtras();
        model = (TripModel) bundle.get("trip");
        initiView();
    }

    void display (EditText editText, String str)
    {
        if (str.equals("null"))
        {
            editText.setText("");
        }
        else
            editText.setText(str);

    }

    Double getValueFromString(String str)
    {
        if (str.equals("null") || str.equals("")) return 0.0;
        return Double.valueOf(str);

    }

    String getDiffDate(String str1, String str2)
    {


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Log.d("time", str1 + "," + str2);
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(str1);
            d2 = format.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long time1, time2;
        if (d2 == null) {time2 = 0; Log.d("tag", "01");} else time2 = d2.getTime();
        if (d1 == null) {time1 = 0;Log.d("tag", "02");} else time1 = d1.getTime();
        long diff = time2 - time1;
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        String result = String.format("%02d:%02d:%02d", diffHours, diffMinutes, diffSeconds);
        return result;

    }
    private void initiView() {
        editText1 = (EditText) findViewById(R.id.editText3);
        editText2 = (EditText) findViewById(R.id.editText4);
        editText3 = (EditText) findViewById(R.id.editText5);
        editText4 = (EditText) findViewById(R.id.editText18);
        editText5 = (EditText) findViewById(R.id.editText6);
        editText6 = (EditText) findViewById(R.id.editText7);
        editText7 = (EditText) findViewById(R.id.editText8);
        editText8 = (EditText) findViewById(R.id.editText9);
        editText9 = (EditText) findViewById(R.id.editText10);
        editText10 = (EditText) findViewById(R.id.editText11);
        editText11 = (EditText) findViewById(R.id.editText12);
        editText12 = (EditText) findViewById(R.id.editText13);
        editText13 = (EditText) findViewById(R.id.editText14);
        editText14 = (EditText) findViewById(R.id.editText19);
        display(editText1, model.getStart_date());
        display(editText2, model.getStart_time());
        display(editText3, model.getStart_latitude());
        display(editText4, model.getStart_longitude());
        display(editText5, model.getStart_odometer());
        display(editText6, model.getPurpose());
        display(editText7, model.getClient());
        display(editText8, model.getEnd_date());
        display(editText9, model.getEnd_time());
        display(editText11, model.getEnd_odometer());
//        display(editText14, model.getStart_date());
//        display(editText11, model.getStart_date());
//        editText1.setText(model.getStart_date());
//        editText2.setText(model.getStart_time());
//        editText3.setText(model.getStart_latitude());
//        editText4.setText(model.getStart_longitude());
//        editText5.setText(model.getStart_odometer());
//        editText6.setText(model.getPurpose());
//        editText7.setText(model.getClient());
//        editText8.setText(model.getEnd_date());
//        editText9.setText(model.getEnd_time());
//
//        editText11.setText(model.getEnd_odometer());
         editText13.setText(getDiffDate(model.getEnd_date() + " " + model.getEnd_time(), model.getStart_date() + " " + model.getStart_time()));
        editText1.setKeyListener(null);
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_flag = 1;
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        UpdateTripActivity.this,
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
                        UpdateTripActivity.this,
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
//                newTripPost();
                new PostTask().execute();
            }
        });
        editText8.setKeyListener(null);
        editText8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        UpdateTripActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setThemeDark(false);
                dpd.vibrate(true);
                dpd.dismissOnPause(true);
                dpd.showYearPickerFirst(true);
                dpd.setTitle("End Date");
                dpd.show(getFragmentManager(), DATEPICKER);
                date_flag = 2;
            }
        });
        editText9.setKeyListener(null);
        editText9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_flag = 2;
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        UpdateTripActivity.this,
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
        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), LocationPickerActivity.class);
//                intent.putExtra(LocationPickerActivity.LATITUDE, 41.4036299);
//                intent.putExtra(LocationPickerActivity.LONGITUDE, 2.1743558);
//                intent.putExtra("test", "this is a test");
//                startActivityForResult(intent, 1);

            }
        });
        GPSTracker tracker = new GPSTracker(this);

//        editText3.setText(String.format("%.3f", tracker.getLatitude()));
//        editText4.setText(String.format("%.3f", tracker.getLongitude()));
        editText10.setText(String.format("%.3f", tracker.getLatitude()));
        editText14.setText(String.format("%.3f", tracker.getLongitude()));
        editText5.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_NEXT ||
                        actionId == EditorInfo.IME_ACTION_GO || keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    {
                        // the user is done typing.
                        editText12.setText(String.valueOf(getValueFromString(editText11.getText().toString()) - getValueFromString(editText5.getText().toString())));
                      //  Toast.makeText(getApplicationContext(), "sadfa", Toast.LENGTH_LONG).show();
                        return true; // consume.
                    }
                }
                return false; // pass on to other listeners.
            }
        });

        editText11.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_NEXT ||
                        actionId == EditorInfo.IME_ACTION_GO || keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    {
                        // the user is done typing.
                   //     Toast.makeText(getApplicationContext(), "sadfa", Toast.LENGTH_LONG).show();
                        editText12.setText(String.valueOf(getValueFromString(editText11.getText().toString()) - getValueFromString(editText5.getText().toString())));
                        return true; // consume.
                    }
                }
                return false; // pass on to other listeners.
            }
        });
    }

    private void UpdateTripPost()
    {
//        editText1.setText(model.getStart_date());
//        editText2.setText(model.getStart_time());
//        editText3.setText(model.getStart_latitude());
//        editText4.setText(model.getStart_longitude());
//        editText5.setText(model.getStart_odometer());
//        editText6.setText(model.getPurpose());
//        editText7.setText(model.getClient());
//        editText8.setText(model.getEnd_date());
//        editText9.setText(model.getEnd_time());
//        editText10.setText(model.getEnd_latitude());
//        editText14.setText(model.getEnd_longitude());
//        editText11.setText(model.getEnd_odometer());
        start_date = editText1.getText().toString().trim();
        start_time = editText2.getText().toString().trim();
        start_latitude = editText3.getText().toString().trim();
        start_longitude = editText4.getText().toString().trim();
        start_octom = editText5.getText().toString().trim();
        client = editText6.getText().toString().trim();
        purpose = editText7.getText().toString().trim();
        end_date = editText8.getText().toString().trim();
        end_time = editText9.getText().toString().trim();
        end_latitude = editText10.getText().toString().trim();
        end_longitude = editText14.getText().toString().trim();
        end_octom = editText11.getText().toString().trim();
        editText12.setText(String.valueOf(getValueFromString(model.getEnd_odometer()) - getValueFromString(model.getStart_odometer())));
        Log.d("start_time", start_time);
        String url = Constants.server_url + Constants.all_trips + "/" + model.getID();
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM) //this is what I say in my POSTman (Chrome plugin)
                .addFormDataPart(Constants.start_time, start_time)
                .addFormDataPart(Constants.start_date, start_date)
                .addFormDataPart(Constants.start_longitude, start_longitude)
                .addFormDataPart(Constants.start_latitude, start_latitude)
                .addFormDataPart(Constants.start_odometer, start_octom)
                .addFormDataPart(Constants.end_date, end_date)
                .addFormDataPart(Constants.end_time, end_time)
                .addFormDataPart(Constants.end_latitude, end_latitude)
                .addFormDataPart(Constants.end_longitude, end_longitude)
                .addFormDataPart(Constants.end_odometer, end_octom)
                .build();
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request .Builder()
                .url(url)
                .addHeader(Constants.auth_token, Constants.str_token)
                .put(requestBody)
                .build();
        try {
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            String responseString = response.body().string();

            response.body().close();
            // do whatever you need to do with responseString
            Log.d("tag", responseString);
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
        editText13.setText(getDiffDate(editText8.getText().toString() + " " + editText9.getText().toString(), editText1.getText().toString() + " " + editText2.getText().toString()));
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

        editText13.setText(getDiffDate(editText8.getText().toString() + " " + editText9.getText().toString(), editText1.getText().toString() + " " + editText2.getText().toString()));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1, menu);
        return true;
    }

    class PostTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {

            showdialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UpdateTripPost();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            hidedialog();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.update:
//                Toast.makeText(this, "开始游戏", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
