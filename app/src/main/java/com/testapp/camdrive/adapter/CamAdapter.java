package com.testapp.camdrive.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.testapp.camdrive.R;
import com.testapp.camdrive.models.CameraModel;

import java.util.List;

public class CamAdapter extends RecyclerView.Adapter<CamAdapter.camView> {

    private List<CameraModel> itemList;
    private Context context;
    private String color;

    public CamAdapter(List<CameraModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    static class camView extends RecyclerView.ViewHolder{

        private TextView camName, camOnline, camModel;
        private RelativeLayout rl;

        camView(@NonNull View itemView) {
            super(itemView);
            camName = itemView.findViewById(R.id.cam_title);
            camModel = itemView.findViewById(R.id.cam_model);
            camOnline = itemView.findViewById(R.id.online_server);
            rl = itemView.findViewById(R.id.color);

        }
    }

    @NonNull
    @Override
    public camView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cam, viewGroup, false);
        SharedPreferences savedColor = context.getSharedPreferences("color", Context.MODE_PRIVATE);
        color = savedColor.getString("camera_color", "Black");
        return new camView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull camView holder, int i) {
        CameraModel item = itemList.get(i);
        holder.camName.setText(item.getNameCam());
        holder.camModel.setText(item.getModelCam());
        String online;
        if (item.getStatCam()){
            online = context.getString(R.string.online);
        }
        else{
            online = context.getString(R.string.offline);
        }
        holder.camOnline.setText(online);
        switch (color) {
            case "Black":
                holder.rl.setBackgroundColor(Color.rgb(34,34,34));
                holder.camName.setTextColor(Color.WHITE);
                holder.camModel.setTextColor(Color.WHITE);
                holder.camOnline.setTextColor(Color.WHITE);
                break;
            case "Blue":
                holder.rl.setBackgroundColor(Color.rgb(0, 153, 203));
                holder.camName.setTextColor(Color.BLACK);
                holder.camModel.setTextColor(Color.BLACK);
                holder.camOnline.setTextColor(Color.BLACK);
                break;
            case "Green":
                holder.rl.setBackgroundColor(Color.rgb(102, 153, 0));
                holder.camName.setTextColor(Color.BLACK);
                holder.camModel.setTextColor(Color.BLACK);
                holder.camOnline.setTextColor(Color.BLACK);
                break;
            case "Red":
                holder.rl.setBackgroundColor(Color.rgb(204,0,1));
                holder.camName.setTextColor(Color.BLACK);
                holder.camModel.setTextColor(Color.BLACK);
                holder.camOnline.setTextColor(Color.BLACK);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(CameraModel item){
        itemList.add(item);
        this.notifyDataSetChanged();
    }

}
