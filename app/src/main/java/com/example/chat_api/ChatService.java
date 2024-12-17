package com.example.chat_api;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatService extends Service {

    // Binder 클래스: 액티비티에서 서비스에 접근할 때 사용
    private final IBinder binder = new ChatBinder();

    public class ChatBinder extends Binder {
        public ChatService getService() {
            return ChatService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    public void getAnswer_emoji(String question, ChatCallback callback) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        ApiRequest request = new ApiRequest(
                "gpt-3.5-turbo",
                Arrays.asList(
                        //컨셉부여하기.

                        new ApiRequest.Message(
                                "system",
                                "You are an AI that answers questions **only using emojis**. "
                                        + "Rules to follow: "
                                        + "1. All answers must consist of 1 to 10 emojis only. No words are allowed, in any language. "
                                        + "2. For time-related questions, represent the most appropriate time using emojis. "
                                        + "Example: Q: 'What time should I meet my friend?' A: ⏰🕘 (Clock pointing to 9). "
                                        + "3. For 'this or that' questions, choose one option and express it clearly with relevant emojis (1 to 10 emojis). "
                                        + "Example: Q: 'Should I eat or go exercise?' A: 🏃‍♂️💪 (Exercise-related emojis)."
                        ),
                        new ApiRequest.Message("user", question)
                )
        );

        Call<ApiResponse> call = apiService.getChatResponse(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String answer = response.body().getChoices().get(0).getMessage().getContent();
                    callback.onSuccess(answer);
                } else {
                    callback.onError("오류가 발생했습니다.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callback.onError("네트워크 오류가 발생했습니다.");
            }
        });
    }



    public void getAnswer(String question, ChatCallback callback) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        ApiRequest request = new ApiRequest(
                "gpt-3.5-turbo",
                Arrays.asList(
                        //컨셉부여하기.

                        new ApiRequest.Message(
                                "system",
                                "You are an AI that gives very short answers in informal tone. "
                                        + "If the question is in Korean, respond in informal Korean. "
                                        + "If the question is in English, respond in simple and short English. "
                                        + "Rules to follow: "
                                        + "1. All answers must be a single sentence, under 10 words. "
                                        + "2. If the question is too complicated, just provide a vague response. "
                                        + "3. It's okay to sound rude. "
                                        + "4. You can use any special characters except '!'. "
                                        + "5. For time-related questions, suggest a reasonable time in KST (Korean Standard Time). "
                                        + "Example: 'What time should I meet my friend tomorrow?' -> '11 AM.'"
                        ),
                        new ApiRequest.Message("user", question)
                )
        );

        Call<ApiResponse> call = apiService.getChatResponse(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String answer = response.body().getChoices().get(0).getMessage().getContent();
                    callback.onSuccess(answer);
                } else {
                    callback.onError("오류가 발생했습니다.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callback.onError("네트워크 오류가 발생했습니다.");
            }
        });
    }


    // 콜백 인터페이스
    public interface ChatCallback {
        void onSuccess(String answer);
        void onError(String error);
    }
}
