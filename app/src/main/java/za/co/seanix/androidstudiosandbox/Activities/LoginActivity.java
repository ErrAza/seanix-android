package za.co.seanix.androidstudiosandbox.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import za.co.seanix.androidstudiosandbox.R;
import za.co.seanix.androidstudiosandbox.Utils.PasswordAuthentication;
import za.co.seanix.androidstudiosandbox.Utils.RequestQueueSingleton;

public class LoginActivity extends AppCompatActivity {

    private static final String login_url = "http://www.seanix.co.za/android/android_login.php";
    private static final String check_url = "http://www.seanix.co.za/android/android_checkuser.php";
    private static final String requestTag = "Login";

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;

    private String login_name;
    private String login_pass;
    private String hashed_pass;
    private String user_salt;

    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setEnabled(true);
        mCtx = this;
    }

    public void UserLogin(View view)
    {
        login_name = txtUsername.getText().toString();
        login_pass = txtPassword.getText().toString();

        if (login_name.isEmpty() || login_pass.isEmpty())
        {
            Toast.makeText(this, "Username or Password cannot be empty!", Toast.LENGTH_LONG).show();
            return;
        }

        btnLogin.setEnabled(false);

        FetchSaltRequest();
    }

    public void ShowUserRegistration(View view)
    {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    public void PostLoginSuccess()
    {
        startActivity(new Intent(this, AndroidVersionsActivity.class));
    }

    private void FetchSaltRequest()
    {
        StringRequest postRequest = new StringRequest(Request.Method.POST, check_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("USER INFO NOT FOUND") && !response.isEmpty())
                        {
                            user_salt = response;
                            hashed_pass = PasswordAuthentication.Hash(login_pass, user_salt);
                            if (!hashed_pass.isEmpty())
                            {
                                LoginRequest();
                            }
                        }
                        else
                        {
                            Toast.makeText(mCtx, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
             )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("login_name", login_name);
                return params;
            }
        };

        postRequest.setTag(requestTag);

        RequestQueueSingleton.getInstance(mCtx).addToRequestQueue(postRequest);
    }

    private void LoginRequest()
    {
        StringRequest postRequest = new StringRequest(Request.Method.POST, login_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("SUCCESS"))
                        {
                            PostLoginSuccess();
                        }
                        else
                        {
                            Toast.makeText(mCtx, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("login_name", login_name);
                params.put("login_pass", hashed_pass);
                params.put("login_salt", user_salt);
                return params;
            }
        };

        postRequest.setTag(requestTag);

        RequestQueueSingleton.getInstance(mCtx).addToRequestQueue(postRequest);
    }

}
