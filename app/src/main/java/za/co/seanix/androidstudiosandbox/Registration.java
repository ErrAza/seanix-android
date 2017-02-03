package za.co.seanix.androidstudiosandbox;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Registration extends AppCompatActivity {

    EditText _txtName;
    EditText _txtUsername;
    EditText _txtPassword;

    String _name;
    String _userName;
    String _userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        _txtName = (EditText) findViewById(R.id.txtName);
        _txtUsername = (EditText) findViewById(R.id.txtUsername);
        _txtPassword = (EditText) findViewById(R.id.txtPassword);
    }

    public void RegisterNewUser(View view)
    {
        _name = _txtName.getText().toString();
        _userName = _txtUsername.getText().toString();
        _userPass = _txtPassword.getText().toString();

        AlertDialog alertDialog = new AlertDialog.Builder(Registration.this).create();
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if (_name.isEmpty())
        {
            alertDialog.setTitle("Error");
            alertDialog.setMessage("No name supplied.");
            alertDialog.show();
            return;
        }

        if (_userName.isEmpty())
        {
            alertDialog.setTitle("Error");
            alertDialog.setMessage("No User Name supplied.");
            alertDialog.show();
            return;
        }

        if (_userPass.isEmpty())
        {
            alertDialog.setTitle("Error");
            alertDialog.setMessage("No Password supplied.");
            alertDialog.show();
            return;
        }

        String method = "register";
        RegistrationTask _registrationTask = new RegistrationTask(this);
        _registrationTask.execute(method, _name, _userName, _userPass);

        finish();

    }
}
