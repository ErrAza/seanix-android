package za.co.seanix.androidstudiosandbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText txtUsername;
    EditText txtPassword;
    String userName;
    String userPassword;

    String[] android_versions = {"Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich",
    "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Nougat"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
    }

    public void UserLogin(View view)
    {
        userName = txtUsername.getText().toString();
        userPassword = txtPassword.getText().toString();

        if (userName.isEmpty() || userPassword.isEmpty())
        {
            Toast.makeText(this, "Username or Password cannot be empty!", Toast.LENGTH_LONG).show();
            return;
        }

        String method = "login";
        /*BackgroundTask backgroundTask = new BackgroundTask(this, BackgroundTask.Task.login);
        backgroundTask.execute(method, userName, userPassword);*/
    }

    public void ShowUserRegistration(View view)
    {
        startActivity(new Intent(this, Registration.class));
    }

}
