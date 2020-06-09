package com.example.eepislibrary.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eepislibrary.R;
import com.example.eepislibrary.api.ApiClient;
import com.example.eepislibrary.api.ApiInterface;
import com.example.eepislibrary.databinding.ActivityLoginBinding;
import com.example.eepislibrary.utils.Session;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
//    private static final String TAG = "LoginActivity";

    private ActivityLoginBinding b;
    private ProgressDialog progressDialog;
    private String email;
    private Session session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        init();

    }

    private void init(){
        session = new Session(getApplicationContext());
        initButton();
    }

    private void initButton(){
        b.btnLogin.setOnClickListener(v -> login());

        b.inputEmail.setOnFocusChangeListener((v, hasFocus) -> {
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
        });

        b.inputPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String password = Objects.requireNonNull(b.inputPassword.getText()).toString();

                if (password.isEmpty()) {
                    b.tilPassword.setError(getString(R.string.error_input_password));
                } else {
                    b.tilPassword.setError(null);
                }
            }
        });
    }

    private void login() {
        if (!validate()) {
            return;
        }
        b.btnLogin.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.LoginTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.login_message_auth));
        progressDialog.show();

        email = Objects.requireNonNull(b.inputEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(b.inputPassword.getText()).toString().trim();

        loginApiRequest(email, password);
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

    public void loginApiRequest(final String email, String password){
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = api.postLogin(email, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> res) {
                if (res.isSuccessful() && res.body() != null) {
                    String jsonResponse = res.body();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);

                        String status = jsonObject.getString("status");
                        if(status.equals("success")){
                            onLoginSuccess(
                                    jsonObject.getString("id_user"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("token")
                            );
                        }
                        else{
                            onLoginFailed(jsonObject.getString("reason"));
                        }

                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        b.btnLogin.setEnabled(true);
                        Snackbar.make(b.rootLogin, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG)
                                .show();
                    }
                }
                else{
                    progressDialog.dismiss();
                    b.btnLogin.setEnabled(true);
                    Snackbar.make(b.rootLogin, R.string.system_error, Snackbar.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                b.btnLogin.setEnabled(true);
                Snackbar.make(b.rootLogin, Objects.requireNonNull(t.getMessage()), Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    public void onLoginFailed(String reason) {
        progressDialog.dismiss();
        b.btnLogin.setEnabled(true);
        Snackbar.make(b.rootLogin, reason, Snackbar.LENGTH_LONG)
                .show();
    }

    public void onLoginSuccess(String id_user, String name, String token) {
        progressDialog.dismiss();
        b.btnLogin.setEnabled(true);
        Snackbar.make(b.rootLogin, token, Snackbar.LENGTH_LONG)
                .show();

        session.setSession(id_user, email, name, token);

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
