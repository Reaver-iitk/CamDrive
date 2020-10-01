
package com.testapp.camdrive.retromodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Camera {


    @SerializedName("camera_name")
    @Expose
    private String cameraName;
    @SerializedName("camera_connected_server")
    @Expose
    private Boolean cameraConnectedServer;
    @SerializedName("camera_model")
    @Expose
    private String cameraModel;


    public String getCameraName() {
        return cameraName;
    }

    public Boolean getCameraConnectedServer() {
        return cameraConnectedServer;
    }

    public String getCameraModel() {
        return cameraModel;
    }

    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public void setCameraConnectedServer(Boolean cameraConnectedServer) {
        this.cameraConnectedServer = cameraConnectedServer;
    }
}
