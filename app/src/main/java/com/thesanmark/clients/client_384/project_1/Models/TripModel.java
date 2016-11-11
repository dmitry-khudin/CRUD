package com.thesanmark.clients.client_384.project_1.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import com.thesanmark.clients.client_384.project_1.Utility.Constants;

/**
 * Created by bryden on 11/9/16.
 */

public class TripModel implements Serializable{
    private String ID = null;
    private String start_date = null;
    private String start_time = null;
    private String start_longitude = null;
    private String start_latitude = null;
    private String start_odometer = null;
    private String client = null;
    private String purpose = null;
    private String end_date = null;
    private String end_time = null;
    private String end_longitude = null;
    private String end_latitude = null;
    private String end_odometer = null;


    public TripModel(String ID, String start_date, String start_time, String start_longitude, String start_latitude, String start_odometer, String client, String purpose, String end_date, String end_time, String end_longitude, String end_latitude, String end_odometer) {
        this.ID = ID;
        this.start_date = start_date;
        this.start_time = start_time;
        this.start_longitude = start_longitude;
        this.start_latitude = start_latitude;
        this.start_odometer = start_odometer;
        this.client = client;
        this.purpose = purpose;
        this.end_date = end_date;
        this.end_time = end_time;
        this.end_longitude = end_longitude;
        this.end_latitude = end_latitude;
        this.end_odometer = end_odometer;
    }

    public TripModel() {
    }

    public String getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(String start_latitude) {
        this.start_latitude = start_latitude;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getEnd_longitude() {
        return end_longitude;
    }

    public void setEnd_longitude(String end_longitude) {
        this.end_longitude = end_longitude;
    }

    public String getEnd_latitude() {
        return end_latitude;
    }

    public void setEnd_latitude(String end_latitude) {
        this.end_latitude = end_latitude;
    }

    public String getEnd_odometer() {
        return end_odometer;
    }

    public void setEnd_odometer(String end_odometer) {
        this.end_odometer = end_odometer;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(String start_longitude) {
        this.start_longitude = start_longitude;
    }

    public String getStart_odometer() {
        return start_odometer;
    }

    public static TripModel getFromJson(JSONObject jsonObject)
    {
        TripModel model;
        model = new TripModel();
        try {
            model.setClient(jsonObject.getString(Constants.client_name));
            model.setEnd_date(jsonObject.getString(Constants.end_date));
            model.setEnd_latitude(jsonObject.getString(Constants.end_latitude));
            model.setEnd_longitude(jsonObject.getString(Constants.end_longitude));
            model.setEnd_odometer(jsonObject.getString(Constants.end_odometer));
            model.setEnd_time(jsonObject.getString(Constants.end_time));
            model.setPurpose(jsonObject.getString(Constants.purpose));
            model.setStart_date(jsonObject.getString(Constants.start_date));
            model.setStart_time(jsonObject.getString(Constants.start_time));
            model.setStart_latitude(jsonObject.getString(Constants.start_latitude));
            model.setStart_longitude(jsonObject.getString(Constants.start_longitude));
            model.setStart_odometer(jsonObject.getString(Constants.start_odometer));
            model.setID(jsonObject.getString(Constants.ID));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return model;
    }
    public void setStart_odometer(String start_odometer) {
        this.start_odometer = start_odometer;
    }
}
