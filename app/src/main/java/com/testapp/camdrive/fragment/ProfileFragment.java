package com.testapp.camdrive.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.testapp.camdrive.LoadDialog;
import com.testapp.camdrive.retrofit.ApiService;
import com.testapp.camdrive.auth.Authorization;
import com.testapp.camdrive.R;
import com.testapp.camdrive.retrofit.RetrofitCore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private Button exit;
    private String Login;
    private TextView tvLogin;
    private ApiService api;
    private LoadDialog load;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences sPref = requireContext().getSharedPreferences("username", Context.MODE_PRIVATE);
        Login = sPref.getString("login_user", "False");
        tvLogin = view.findViewById(R.id.textLogin);
        tvLogin.setText(Login);
        exit = view.findViewById(R.id.btn_ext);

        api = RetrofitCore.getApiService(requireContext());
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load = new LoadDialog();
                load.show(getActivity().getSupportFragmentManager(),"");
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("Autologin", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.putString("Autologin", "False").apply();
                ed.commit();
                OnlineExit();
            }
        });

        return view;
    }

    private void OnlineExit(){
        Call<com.testapp.camdrive.retromodel.Login> call = api.getExit();
        call.enqueue(new Callback<com.testapp.camdrive.retromodel.Login>() {
            @Override
            public void onResponse(Call<com.testapp.camdrive.retromodel.Login> call, Response<com.testapp.camdrive.retromodel.Login> response) {
                assert response.body() != null;
                if(response.body().getStatus() == 1){
                    Log.e("Вывод из пользователя", " - успешно");
                    Intent main = new Intent(getActivity(), Authorization.class);
                    startActivity(main);
                    getActivity().finish();
                }else{
                    load.dismiss();
                    Toast.makeText(getContext(), R.string.error_server,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<com.testapp.camdrive.retromodel.Login> call, Throwable t) {
                load.dismiss();
                Toast.makeText(getContext(), R.string.error_server,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}