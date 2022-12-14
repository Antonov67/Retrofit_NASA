package ru.myitschool.mte.data;


import com.google.gson.annotations.SerializedName;

public class CameraData {
    public int id;
    public String name;
    @SerializedName("rover_id")
    public int rover;
    @SerializedName("full_name")
    public String fullName;
}
