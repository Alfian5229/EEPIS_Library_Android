package com.example.eepislibrary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eepislibrary.R;
import com.example.eepislibrary.api.ApiClient;
import com.example.eepislibrary.api.ApiInterface;
import com.example.eepislibrary.utils.Session;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSession();
    }

    private void checkSession() {
        session = new Session(getApplicationContext());
        if(!session.getToken().equals("empty")){
            checkToken();
        }
        else{
            session.destroySession();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }

    private void checkToken() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = api.postCekToken(session.getEmail(), session.getToken());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> res) {
                if (res.isSuccessful() && res.body() != null) {
                    String jsonResponse = res.body();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);

                        String status = jsonObject.getString("status");
                        if(status.equals("success")){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        else{
                            session.destroySession();
                            Toast.makeText(SplashActivity.this, R.string.expired_token_message, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(SplashActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
                else{
                    Toast.makeText(SplashActivity.this, R.string.system_error, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(SplashActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}