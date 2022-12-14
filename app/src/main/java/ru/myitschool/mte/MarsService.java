package ru.myitschool.mte;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.myitschool.mte.data.Report;

public interface MarsService {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    Call<Report> getPhotos(@Query("earth_date") String earth_date, @Query("camera") String camera, @Query("api_key") String api_key);
}