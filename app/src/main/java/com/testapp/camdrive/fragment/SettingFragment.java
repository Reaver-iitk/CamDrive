package com.testapp.camdrive.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.testapp.camdrive.R;

public class SettingFragment extends Fragment implements View.OnClickListener{


    private Button bntNow, btnBlack, btnBlue, btnGreen, btnRed;
    private String color;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_setting, container, false);
        SharedPreferences savedColor = requireContext().getSharedPreferences("color", Context.MODE_PRIVATE);
        color = savedColor.getString("camera_color", "Black");
        bntNow = view.findViewById(R.id.colorNow);
        btnBlack = view.findViewById(R.id.colorBlack);
        btnBlue = view.findViewById(R.id.colorBlue);
        btnGreen = view.findViewById(R.id.colorGreen);
        btnRed = view.findViewById(R.id.colorRed);
        btnBlack.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnRed.setOnClickListener(this);

        switch (color) {
            case "Black":
                bntNow.setBackgroundResource(R.drawable.round_button_bk);
                break;
            case "Blue":
                bntNow.setBackgroundResource(R.drawable.round_button_bl);
                break;
            case "Green":
                bntNow.setBackgroundResource(R.drawable.round_button_gr);
                break;
            case "Red":
                bntNow.setBackgroundResource(R.drawable.round_button_rd);
                break;
        }





        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.colorBlack:
                ColorSave("Black");
                bntNow.setBackgroundResource(R.drawable.round_button_bk);
                break;
            case R.id.colorBlue:
                ColorSave("Blue");
                bntNow.setBackgroundResource(R.drawable.round_button_bl);
                break;
            case R.id.colorGreen:
                ColorSave("Green");
                bntNow.setBackgroundResource(R.drawable.round_button_gr);
                break;
            case R.id.colorRed:
                ColorSave("Red");
                bntNow.setBackgroundResource(R.drawable.round_button_rd);
                break;
        }
    }

    private void ColorSave(String color){
        SharedPreferences colorPref = requireContext().getSharedPreferences("color", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = colorPref.edit();
        ed.putString("camera_color", color).apply();
        ed.commit();
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