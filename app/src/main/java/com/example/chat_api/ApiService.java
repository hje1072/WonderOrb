package com.example.chat_api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @POST("v1/chat/completions") // OpenAI API의 경로
    Call<ApiResponse> getChatResponse(@Body ApiRequest request);
}