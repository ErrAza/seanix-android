package za.co.seanix.androidstudiosandbox;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

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

    private String name, userName, userPass, salt, hashedPass;

    public RegistrationTask(Context ctx) {
        CONTEXT = ctx;
    }

    @Override
    protected void onPreExecute() {
        ALERTDIALOG = new AlertDialog.Builder(CONTEXT).create();
        ALERTDIALOG.setTitle("Registration Information...");
    }
    @Override
    protected String doInBackground(String... params) {

        name = params[1];
        userName = params[2];
        userPass = params[3];
        salt = PasswordAuthentication.GenerateNewSalt(12);
        hashedPass = PasswordAuthentication.Hash(userPass, salt);
        userPass = "";

        try {
            URL url = new URL(reg_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            // Send login request
            String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                    URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&" +
                    URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(hashedPass, "UTF-8") + "&" +
                    URLEncoder.encode("salt", "UTF-8") + "=" + URLEncoder.encode(salt, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String line = "";

            RESPONSE = "";

            while((line = bufferedReader.readLine()) != null)
            {
                RESPONSE += line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return RESPONSE;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
        Toast.makeText(CONTEXT, result, Toast.LENGTH_LONG).show();
        if (result == "SUCCESS")
        {
            //DO something
        }
    }



}
