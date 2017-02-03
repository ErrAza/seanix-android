package za.co.seanix.androidstudiosandbox;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

/**
 * Created by Sean on 1/27/2017.
 */

public abstract class BackgroundTask extends AsyncTask<String, Void, String> {

    AlertDialog ALERTDIALOG;
    Context CONTEXT;
    String RESPONSE;

    @Override
    protected void onPreExecute()
    {
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate();
    }

    @Override
    protected void onPostExecute(String result) {
    }
}
