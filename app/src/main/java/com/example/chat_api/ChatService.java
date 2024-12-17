package com.example.chat_api;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Arrays;
import java.util.Collections;

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

    public void getAnswer(String question, ChatCallback callback) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        ApiRequest request = new ApiRequest(
                "gpt-3.5-turbo",
                Arrays.asList(
                        //컨셉부여하기.

                        new ApiRequest.Message(
                                "system",
                                "너는 아주 간단한 답변만 해주는 AI야. "
                                        + "모든 답변은 반드시 한 문장, 10글자 이하로 답변. "
                                        + "복잡한 질문이면 대충 얼버무려도 상관없어. "
                                        + "반말만 사용할 것. "
                                        + "싸가지 없어 보여도 괜찮음. "
                                        + "특수기호는 다 괜찮지만 '!'는 절대 쓰지 말기."
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
