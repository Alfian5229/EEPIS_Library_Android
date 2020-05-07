package com.example.eepislibrary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.eepislibrary.R;
import com.example.eepislibrary.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private ActivityLoginBinding b;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        b.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        b.inputEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String email = Objects.requireNonNull(b.inputEmail.getText()).toString();

                    if (email.isEmpty()) {
                        b.tilEmail.setError(getString(R.string.error_input_email));
                    } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        b.tilEmail.setError(getString(R.string.error_input_email_validation));
                    }
                    else {
                        b.tilEmail.setError(null);
                    }
                }
            }
        });

        b.inputPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String password = Objects.requireNonNull(b.inputPassword.getText()).toString();

                    if (password.isEmpty()) {
                        b.tilPassword.setError(getString(R.string.error_input_password));
                    } else {
                        b.tilPassword.setError(null);
                    }
                }
            }
        });
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            return;
        }
        b.btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.LoginTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.login_message_auth));
        progressDialog.show();

        String email = Objects.requireNonNull(b.inputEmail.getText()).toString();
        String password = Objects.requireNonNull(b.inputPassword.getText()).toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        b.btnLogin.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Snackbar.make(findViewById(R.id.root_login), R.string.error_login_failed, Snackbar.LENGTH_LONG)
                .show();
        b.btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = Objects.requireNonNull(b.inputEmail.getText()).toString();
        String password = Objects.requireNonNull(b.inputPassword.getText()).toString();

        if (email.isEmpty()) {
            b.tilEmail.setError(getString(R.string.error_input_email));
            valid = false;
        } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            b.tilEmail.setError(getString(R.string.error_input_email_validation));
            valid = false;
        }
        else {
            b.tilEmail.setError(null);
        }

        if (password.isEmpty()) {
            b.tilPassword.setError(getString(R.string.error_input_password));
            valid = false;
        } else {
            b.tilPassword.setError(null);
        }

        return valid;
    }

}
