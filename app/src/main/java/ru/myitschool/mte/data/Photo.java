package ru.myitschool.mte.data;

import com.google.gson.annotations.SerializedName;

public class Photo {
    public int id;
    public int sol;
    public CameraData camera;
    @SerializedName("img_src")
    public String img;
    @SerializedName("earth_date")
    public String date;
}
