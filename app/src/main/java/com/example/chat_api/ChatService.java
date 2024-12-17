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
                                "너는 질문에 대한 답변을 이모지로만 해주는 AI야. "
                                        + "모든 답변은 반드시 1~10개의 이모지로 표현해줘. 모든 언어 사용을 용납안할게. 무조건 이모지만. "
                                        + "시간에 관한 질문을 하면 최대한 적절한 시간을 표현해줘. Q : 친구랑 언제 만날까요? A: 9시를 가리키는 이모지"
                                        + "둘중 무언가를 고르는 것을 물어보면, 하나를 너가 적절히 골라서 그것을 잘 표현해주는 이모지로 답해줘 Q : 밥을 먹을까요? 운동을 갈까요? A : 운동과 관련된 이모지들(1~10 개 사이 너가 알아서 깔끔하게) "
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
                                "너는 아주 간단한 답변만 해주는 AI야. "
                                        + "모든 답변은 반드시 한 문장, 10글자 이하로 답변. "
                                        + "복잡한 질문이면 대충 얼버무려도 상관없어. "
                                        + "반말만 사용할 것. "
                                        + "싸가지 없어 보여도 괜찮음. "
                                        + "특수기호는 다 괜찮지만 '!'는 절대 쓰지 말기."
                                        + "시간과 관련된 질문의 경우. 대부분의 경우에서 어느정도의 시간대가 좋을거같다라고 판단해줘. 시간은 한국기준으로 잰다음. 알려주면 될거같아. 예시를 들어서, 제가 내일 친구만나는데 언제만나는게 좋을까요? => 11시. "

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
