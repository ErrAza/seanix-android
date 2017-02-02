package za.co.seanix.androidstudiosandbox;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

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
 * Created by Sean on 2/2/2017.
 */

public class RegistrationTask extends BackgroundTask {

    private static final String reg_url = "http://www.seanix.co.za/android/android_adduser.php";
    private static final String check_url = "http://www.seanix.co.za/android/android_checkuser.php";

    private String name, userName, userPass, salt, hashedPass;


    @Override
    protected void onPreExecute() {
        _alertDialog = new AlertDialog.Builder(_ctx).create();
        _alertDialog.setTitle("Login Information...");
    }
    @Override
    protected String doInBackground(String... params) {
        Register();
        return response;
    }

    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }



    public RegistrationTask(Context ctx, String[]params) {
        _ctx = ctx;
        name = params[1];
        userName = params[2];
        userPass = params[3];
        salt = PasswordAuthentication.GenerateNewSalt(12);
        hashedPass = PasswordAuthentication.Hash(userPass, salt);
        userPass = "";
    }

    private void Register()
    {
        try {
            URL url = new URL(check_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            // Send Login Details First
            String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                    URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String checkResponse = "";
            String line = "";

            while((line = bufferedReader.readLine()) != null)
            {
                checkResponse += line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            if (checkResponse == "SUCCESS")
            {
                // Server has checked there is no conflicting user already in the backend.
            }
            else
            {
                // Server has let us know that a user with that name/username already exists.
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String GetResponse()
    {
        return response;
    }


}
