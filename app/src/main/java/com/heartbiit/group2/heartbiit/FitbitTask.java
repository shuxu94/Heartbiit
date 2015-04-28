package com.heartbiit.group2.heartbiit;

import android.os.AsyncTask;

/**
 * Created by shuxu on 4/26/15.
 */
public class FitbitTask extends AsyncTask<Void, Void, Void> {


    @Override
    protected Void doInBackground(Void... params) {
        FitbitData data = new FitbitData();
        data.run();
        return null;
    }


}
