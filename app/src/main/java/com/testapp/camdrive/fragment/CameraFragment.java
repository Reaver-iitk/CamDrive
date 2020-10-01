package com.testapp.camdrive.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.testapp.camdrive.retrofit.ApiService;
import com.testapp.camdrive.R;
import com.testapp.camdrive.retrofit.RetrofitCore;
import com.testapp.camdrive.adapter.CamAdapter;
import com.testapp.camdrive.models.CameraModel;
import com.testapp.camdrive.retromodel.Camera;
import com.testapp.camdrive.retromodel.OnlineCamera;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraFragment extends Fragment {

    private ApiService api;
    private List<Camera> cameras;
    private ArrayList<CameraModel> result = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressBar progressBar, progressBarR;
    private ImageView ivRefresh;
    private Handler h;
    private Runnable run;
    private static boolean handlerflag=false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_camera, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.updateLoad);
        ivRefresh = view.findViewById(R.id.refresh);
        progressBarR = view.findViewById(R.id.updateBar);
        progressBarR.setVisibility(View.INVISIBLE);
        ivRefresh.setVisibility(View.GONE);
        api = RetrofitCore.getApiService(requireContext());
        progressBar.setProgress(100);
        handlerflag=true;

            h = new Handler();
            run = new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                progressBarR.setVisibility(View.VISIBLE);
                OnlineCam();
                h.postDelayed(this, 10000);
            }
        };
        run.run();
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineCam();
            }
        });

        return view;
    }


    private void OnlineCam(){
        Call<OnlineCamera> call = api.getCamera();
        call.enqueue(new Callback<OnlineCamera>() {
            @Override
            public void onResponse(Call<OnlineCamera> call, Response<OnlineCamera> response) {
                assert response.body() != null;
                if (response.body().getStatus() == 1) {
                    result.removeAll(result);
                    ivRefresh.setVisibility(View.GONE);
                    cameras = response.body().getData().getCameras();
                    int i = 0;
                    while (i < cameras.size()) {
                        result.add(new CameraModel(cameras.get(i).getCameraName(),
                                cameras.get(i).getCameraModel(), cameras.get(i).getCameraConnectedServer()));
                        i++;
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                    recyclerView.setAdapter(new CamAdapter(result, getContext()));
                    progressBar.setProgress(100);
                    progressBar.setVisibility(View.VISIBLE);
                    progressBarR.setVisibility(View.INVISIBLE);
                }else {
                    ivRefresh.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<OnlineCamera> call, Throwable t) {
                ivRefresh.setVisibility(View.VISIBLE);
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
        if(!handlerflag)
        {
            run.run();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        handlerflag=false;
        h.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
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
        h.removeCallbacksAndMessages(null);
    }
}