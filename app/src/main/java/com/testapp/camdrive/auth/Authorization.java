package com.testapp.camdrive.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.testapp.camdrive.LoadDialog;
import com.testapp.camdrive.retrofit.ApiService;
import com.testapp.camdrive.MainActivity;
import com.testapp.camdrive.R;
import com.testapp.camdrive.retrofit.RetrofitCore;
import com.testapp.camdrive.retromodel.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authorization extends AppCompatActivity {
    private EditText ET_login, ET_password;
    private ImageView visible;
    private int visib = 0;
    private ApiService api;
    private String login, password;
    private LoadDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);
        ET_login = findViewById(R.id.Login);
        ET_password =  findViewById(R.id.Password);
        visible =  findViewById(R.id.visibleOn);
        api = RetrofitCore.getApiService(getApplicationContext());



        visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visib == 0) {
                    visible.setImageResource(R.drawable.ic_visibility_off);
                    ET_password.setTransformationMethod(null);
                    ET_password.setSelection(ET_password.length());
                    visib=1;
                }
                else {
                    visible.setImageResource(R.drawable.ic_visibility);
                    ET_password.setTransformationMethod(new PasswordTransformationMethod());
                    ET_password.setSelection(ET_password.length());
                    visib=0;
                }
            }
        });
    }

    private static long back_pressed;
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(getBaseContext(), R.string.close, Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    public void onAutorization(View view)
    {
        if (CheckNetwork()){
            login = ET_login.getText().toString();
            password = ET_password.getText().toString();

            if(TextUtils.isEmpty(login) || TextUtils.isEmpty(password)){
                Toast.makeText(getApplicationContext(), R.string.validate, Toast.LENGTH_LONG).show();
            }else{
                load = new LoadDialog();
                load.show(getSupportFragmentManager(),"");
                SharedPreferences loginPref = getApplicationContext().getSharedPreferences("username", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = loginPref.edit();
                ed.putString("login_user", login).apply();
                ed.commit();
                SharedPreferences passwordPref = getApplicationContext().getSharedPreferences("password", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed2 = passwordPref.edit();
                ed2.putString("password_user", password).apply();
                ed2.commit();
                Online(login,password);

            }
        }
        else {
            Toast.makeText(getApplicationContext(),R.string.check,Toast.LENGTH_LONG).show();
        }
    }


    private void Online (String log, String pas) {

        Call<Login> call = api.getLogin(log, pas);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                assert response.body() != null;
                if (response.body().getStatus() ==1 ){
                    Intent main = new Intent(Authorization.this, MainActivity.class);
                    startActivity(main);
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Autologin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sharedPreferences.edit();
                    ed.putString("Autologin", "True").apply();
                    ed.commit();
                    load.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(),R.string.authError, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getApplicationContext(),R.string.authError, Toast.LENGTH_LONG).show();
                load.dismiss();
            }
        });
    }

    private boolean CheckNetwork() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conMgr != null;
        return conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected();

    }



}
