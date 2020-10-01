package com.testapp.camdrive.models;

public class CameraModel {
    private String nameCam;
    private String modelCam;
    private Boolean statCam;

    public CameraModel(String nameCam, String modelCam, Boolean statCam) {
        this.nameCam = nameCam;
        this.modelCam = modelCam;
        this.statCam = statCam;
    }

    public String getModelCam() {
        return modelCam;
    }

    public void setModelCam(String modelCam) {
        this.modelCam = modelCam;
    }

    public String getNameCam() {
        return nameCam;
    }

    public void setNameCam(String nameCam) {
        this.nameCam = nameCam;
    }

    public Boolean getStatCam() {
        return statCam;
    }

    public void setStatCam(Boolean statCam) {
        this.statCam = statCam;
    }


    public CameraModel(){

    }
}
