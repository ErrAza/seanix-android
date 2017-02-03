package za.co.seanix.androidstudiosandbox;

import android.content.Context;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.util.DebugUtils;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Sean on 2/3/2017.
 */

public class LoginTask extends BackgroundTask {

    private static final String login_url = "http://www.seanix.co.za/android/android_login.php";
    private static final String check_url = "http://www.seanix.co.za/android/android_checkuser.php";

    private String login_name;
    private String login_pass;

    public LoginTask(Context ctx)
    {
        CONTEXT = ctx;
    }

    @Override
    protected void onPreExecute() {
        ALERTDIALOG = new AlertDialog.Builder(CONTEXT).create();
        ALERTDIALOG.setTitle("Login Information...");
    }

    @Override
    protected String doInBackground(String... params) {
        login_name = params[0];
        login_pass = params[1];

        return FetchSalt(login_name);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(CONTEXT, result, Toast.LENGTH_LONG).show();
        if (result == "SUCCESS")
        {
            ALERTDIALOG.setMessage("Login Successful for : " + login_name);
            ALERTDIALOG.show();
        }
    }

    private String AttemptLogin(String login_name, String hashed_pass, String user_salt)
    {
        String result = "";

        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String data = URLEncoder.encode("login_name", "UTF-8") + "=" + URLEncoder.encode(login_name, "UTF-8") + "&" +
                    URLEncoder.encode("login_pass", "UTF-8") + "=" + URLEncoder.encode(hashed_pass, "UTF-8") + "&" +
                    URLEncoder.encode("login_salt", "UTF-8") + "=" + URLEncoder.encode(user_salt, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            RESPONSE = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                RESPONSE += line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return RESPONSE;

        }  catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String FetchSalt(String login_name)
    {
        String result = "";

        try {
            URL url = new URL(check_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String data = URLEncoder.encode("login_name", "UTF-8") + "=" + URLEncoder.encode(login_name, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            result = String.valueOf(httpURLConnection.getResponseCode());
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            RESPONSE = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                RESPONSE += line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            if (!RESPONSE.equals("USER INFO NOT FOUND"))
            {
                String salt = RESPONSE;
                if (!salt.isEmpty())
                {
                    String hashedPass = PasswordAuthentication.Hash(login_pass, salt);
                    return AttemptLogin(login_name, hashedPass, salt);
                }
                return RESPONSE;
            }
            else
            {
                return RESPONSE;
            }

        }  catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }
}
