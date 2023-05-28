package com.example.startracker.entities;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("algorithm/")
    Call<ImageResponse> algorithm(@Body ImageRequest url);

}
