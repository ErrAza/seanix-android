package za.co.seanix.androidstudiosandbox;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Sean on 1/27/2017.
 */

public abstract class BackgroundTask extends AsyncTask<String, Void, String> {

    AlertDialog _alertDialog;
    Context _ctx;
    String response;

    @Override
    protected void onPreExecute()
    {
    }

    @Override
    protected String doInBackground(String... params)
    {
        String login_url = "http://www.seanix.co.za/android/android_login.php";

        String method = params[0];
        if (method.equals("register"))
        {
            RegistrationTask regTask = new RegistrationTask(params);


        }
        else if (method.equals("login"))
        {
            String login_name = params[1];
            String login_pass = params[2];
            String salt = "";

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("login_name", "UTF-8")+"="+URLEncoder.encode(login_name, "UTF-8")+"&"+
                        URLEncoder.encode("login_salt", "UTF-8")+"="+URLEncoder.encode(login_pass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null)
                {
                    response += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate();
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Registration Success..."))
        {
            Toast.makeText(_ctx, result, Toast.LENGTH_LONG).show();
        }
        else
        {
            _alertDialog.setMessage(result);
            _alertDialog.show();
        }
    }
}