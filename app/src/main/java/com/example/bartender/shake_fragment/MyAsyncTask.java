package com.example.bartender.shake_fragment;

import android.os.AsyncTask;

public class MyAsyncTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        //Make API Call;
        return "DATA";
    }
}
