package com.example.bartender;

import android.os.Handler;
import android.os.Looper;

import com.example.bartender.shake_fragment.MyAsyncTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    Set<String> mySet = new HashSet<>(10);

    List<String> myList = new ArrayList<>(10);

    Handler mHandler = new Handler(Looper.getMainLooper());
    String BASE_URL = "http://www.tomtom.com";
    MyAsyncTask task = new MyAsyncTask();

    public static void main(String[] args) {
        makeAPICall();
    }

    private static void makeAPICall(){

        Thread workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = task.doInBackground(BASE_URL);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onPostExecute(result);
                    }
                });
            }
        }).start();

    }
}
