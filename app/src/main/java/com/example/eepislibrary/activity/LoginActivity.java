package com.example.eepislibrary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.eepislibrary.R;
import com.example.eepislibrary.activity.api.PostInterface;
import com.example.eepislibrary.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private ActivityLoginBinding b;
    private ProgressDialog progressDialog;
    private String email;
    private String password;

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
        if (!validate()) {
            return;
        }
        b.btnLogin.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.LoginTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.login_message_auth));
        progressDialog.show();

        String email = Objects.requireNonNull(b.inputEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(b.inputPassword.getText()).toString().trim();

        loginApiRequest(email, password);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public boolean validate() {
        boolean valid = true;

        email = Objects.requireNonNull(b.inputEmail.getText()).toString();
        password = Objects.requireNonNull(b.inputPassword.getText()).toString();

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
        Log.i(TAG, "a");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        PostInterface api = retrofit.create(PostInterface.class);

        Call<String> call = api.postLogin(email, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i(TAG, "b");
                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "success_api");
                    String jsonResponse = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(jsonResponse);

                            String status = jsonObject.getString("status");
                            if(status.equals("sukses")){
                                onLoginSuccess(jsonObject.getString("token"));
                            }
                            else{
                                onLoginFailed(jsonObject.getString("reason"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void onLoginFailed(String reason) {
        progressDialog.dismiss();
        Snackbar.make(findViewById(R.id.root_login), reason, Snackbar.LENGTH_LONG)
                .show();
        b.btnLogin.setEnabled(true);
    }

    public void onLoginSuccess(String token) {
        progressDialog.dismiss();
        b.btnLogin.setEnabled(true);
        Toast.makeText(this, token, Toast.LENGTH_LONG).show();
    }
}
