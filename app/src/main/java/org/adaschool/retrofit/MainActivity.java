package org.adaschool.retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.bumptech.glide.Glide;
import org.adaschool.retrofit.databinding.ActivityMainBinding;
import org.adaschool.retrofit.RetrofitInstance;
import org.adaschool.retrofit.BreedsListDto;
import org.adaschool.retrofit.DogApiService;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DogApiService dogApiService = RetrofitInstance.getRetrofitInstance().create(DogApiService.class);

        Call<BreedsListDto> call = dogApiService.getHounds();
        call.enqueue(new Callback<BreedsListDto>() {
            @Override
            public void onResponse(@NonNull Call<BreedsListDto> call, @NonNull Response<BreedsListDto> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String Dogs = response.body().getMessage();
                    loadDogInfo(Dogs);
                } else {
                    Log.e(TAG, "Error en la respuesta de la API");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BreedsListDto> call, @NonNull Throwable t) {
                Log.e(TAG, "Error al llamar a la API", t);
            }
        });
    }

    private void loadDogInfo(String dogUrl) {
        String dogName = dogUrl.split("/")[4];
        binding.textView.setText(dogName);
        Glide.with(this)
                .load(dogUrl)
                .into(binding.imageView);
    }

}
