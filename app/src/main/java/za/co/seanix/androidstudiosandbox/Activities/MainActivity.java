package za.co.seanix.androidstudiosandbox.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import za.co.seanix.androidstudiosandbox.R;

/**
 * Created by Sean on 2/8/2017.
 */

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegistration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartLoginActivity();
            }
        });

        btnRegistration = (Button) findViewById(R.id.btnRegister);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartRegistrationActivity();
            }
        });
    }

    public void StartLoginActivity()
    {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void StartRegistrationActivity()
    {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}
