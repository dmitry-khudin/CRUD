package com.thesanmark.clients.client_384.project_1.Utility;

/**
 * Created by bryden on 11/8/16.
 */

public class Constants {
    public static String server_url = " http://162.243.174.206";
    public static String login = "/api/auth/users/login";
    public static String userID;
    public static String tripID;
    public static String str_token;
    public static String str_username;
    public static String str_firstname;
    public static String str_lastname;
    public static String str_email;
    public static String validate_session = "/api/auth/users/" + userID + "/validate-session";


    public static String reset_password_request = "/api/auth/users/password-reset-request";
    public static String reset_password = "/api/auth/users/password-reset";
    public static String first_name = "first_name";
    public static String last_name = "last_name";
    public static String ID = "id";
    public static String all_trips = "/api/trips";
    public static String update_trip = "/api/trips/" + tripID;
    public static String delete_trip = "/api/trips/" + tripID;
    public static String single_trip = "/api/trips/" + tripID;
    public static String username = "username";
    public static String password = "password";
    public static String auth_token = "auth-token";
    public static String email = "email";
    public static String password_confirmation = "password_confirmation";
    public static String token = "token";
    public static String start = "start";
    public static String count = "count";
    public static String start_date = "start_date";
    public static String end_date = "end_date";
    public static String end_time = "end_time";
    public static String start_latitude = "start_latitude";
    public static String start_longitude = "start_longitude";
    public static String start_odometer = "start_odometer";
    public static String start_time = "start_time";
    public static String end_latitude = "end_latitude";
    public static String end_longitude = "end_longitude";
    public static String end_odometer = "end_odometer";
    public static String client_name = "client_name";
    public static String purpose = "purpose";



}
