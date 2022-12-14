package ru.myitschool.mte;

import static ru.myitschool.mte.Utils.API_KEY;
import static ru.myitschool.mte.Utils.BASE_URL;
import static ru.myitschool.mte.Utils.BLANK_URL;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.myitschool.mte.data.Report;
import ru.myitschool.mte.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    EditText etDate;
    Button btSearch;
    SwitchMaterial btSwitch;
    WebView wvResult;
    Retrofit retrofit;

    /**
     * Uses API
     * https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=2022-07-01&camera=navcam&api_key=DEMO_KEY
     * https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=2022-02-01&camera=fhaz&api_key=DEMO_KEY
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        etDate = binding.content.etDate;
        btSearch = binding.content.btFind;
        btSwitch = binding.content.switchCamera;
        wvResult = binding.content.wvResult;

        wvResult.getSettings().setJavaScriptEnabled(true);
        wvResult.getSettings().setBuiltInZoomControls(true);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MarsService service = retrofit.create(MarsService.class);
        btSearch.setOnClickListener(view -> {
            wvResult.loadUrl(BLANK_URL);
            Call<Report> call = service.getPhotos(etDate.getText().toString(),
                    btSwitch.isChecked() ? btSwitch.getTextOn().toString() : btSwitch.getTextOff().toString(), API_KEY);
            call.enqueue(new Callback<>() {

                @Override
                public void onResponse(@NonNull Call<Report> call, @NonNull Response<Report> response) {
                    if (response.isSuccessful()) {
                        Report report = response.body();
                        if (report.photos != null) {
                            wvResult.loadUrl(report.photos[0].img);
                        } else {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.nothing), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Report> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.connection_problems), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}



