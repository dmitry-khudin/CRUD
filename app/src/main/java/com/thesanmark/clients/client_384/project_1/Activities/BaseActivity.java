package com.thesanmark.clients.client_384.project_1.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by bryden on 11/8/16.
 */

public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog pDialog;

    protected void showdialog()
    {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
    }

    protected void hidedialog()
    {
        pDialog.dismiss();
    }
}
