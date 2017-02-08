package za.co.seanix.androidstudiosandbox.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegistrationActivity extends AppCompatActivity {

    private static final String reg_url = "http://www.seanix.co.za/android/android_adduser.php";
    private static final String requestTag = "Registration";

    private EditText txtName;
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnRegister;

    private String name;
    private String userName;
    private String userPass;
    private String salt;
    private String hashedPass;

    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        mCtx = this;

        txtName = (EditText) findViewById(R.id.txtName);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setEnabled(true);
    }

    public void RegisterNewUser(View view)
    {
        name = txtName.getText().toString();
        userName = txtUsername.getText().toString();
        userPass = txtPassword.getText().toString();

        AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if (name.isEmpty() || userName.isEmpty() || userPass.isEmpty())
        {
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Incomplete Details");
            alertDialog.show();
            return;
        }

        btnRegister.setEnabled(false);

        RegistrationRequest();
    }

    private void RegistrationRequest()
    {
        salt = PasswordAuthentication.GenerateNewSalt(12);
        hashedPass = PasswordAuthentication.Hash(userPass, salt);
        userPass = "";

        StringRequest postRequest = new StringRequest(Request.Method.POST, reg_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("SUCCESS"))
                        {
                            PostRegistrationRequest();
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
                params.put("user", name);
                params.put("user_name", userName);
                params.put("user_pass", hashedPass);
                params.put("salt", salt);
                return params;
            }
        };

        postRequest.setTag(requestTag);

        RequestQueueSingleton.getInstance(mCtx).addToRequestQueue(postRequest);
    }

    private void PostRegistrationRequest()
    {
        Toast.makeText(mCtx, "SUCCESS", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, LoginActivity.class));
    }

}
