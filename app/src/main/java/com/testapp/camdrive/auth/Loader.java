package com.testapp.camdrive.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.testapp.camdrive.retrofit.ApiService;
import com.testapp.camdrive.MainActivity;
import com.testapp.camdrive.R;
import com.testapp.camdrive.retrofit.RetrofitCore;
import com.testapp.camdrive.retromodel.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Loader extends AppCompatActivity {
    private String Enter;
    private TextView tvPreload;
    private String Login, Password;
    private ApiService api;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preloader);
        tvPreload = findViewById(R.id.preload);
        api = RetrofitCore.getApiService(getApplicationContext());
        SharedPreferences sPref = getSharedPreferences("Autologin", Context.MODE_PRIVATE);
        Enter = sPref.getString("Autologin", "False");
        SharedPreferences sPrefLog = getApplicationContext().getSharedPreferences("username", Context.MODE_PRIVATE);
        Login = sPrefLog.getString("login_user", "False");
        SharedPreferences sPrefPass = getApplicationContext().getSharedPreferences("password", Context.MODE_PRIVATE);
        Password = sPrefPass.getString("password_user", "False");

            if (CheckNetwork()) {
                if (Enter.equals("True")) {
                    if (!Login.equals("False") || !Password.equals("False")) {
                            Online(Login, Password);
                    } else{Intent main = new Intent(Loader.this, Authorization.class);
                            startActivity(main);}
                } else {
                        Intent main = new Intent(Loader.this, Authorization.class);
                        startActivity(main);
                }

            } else {
                final Handler h = new Handler();
                Runnable run = new Runnable() {

                    @Override
                    public void run() {
                        if (CheckNetwork()) {
                            tvPreload.setText(R.string.wait);
                            if (Enter.equals("True")) {
                                Online(Login,Password);
                            } else {
                                Intent main = new Intent(Loader.this, Authorization.class);
                                startActivity(main);
                            }
                            h.removeCallbacks(this);
                        } else {
                            h.postDelayed(this, 2000);
                        }
                    }
                };
                run.run();
                tvPreload.setText(R.string.check);
                Toast.makeText(getApplicationContext(), R.string.check, Toast.LENGTH_LONG).show();
            }
        }

    private boolean CheckNetwork() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conMgr != null;
        return conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected();

    }
    private void Online (String log, String pas) {

        Call<Login> call = api.getLogin(log, pas);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                assert response.body() != null;
                if (response.body().getStatus() ==1 ){
                    Intent main = new Intent(Loader.this, MainActivity.class);
                    startActivity(main);
                }
                else {
                    Intent main = new Intent(Loader.this, Authorization.class);
                    startActivity(main);
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }
}

