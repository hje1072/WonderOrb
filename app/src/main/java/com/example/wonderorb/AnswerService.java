package com.example.wonderorb;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.chat_api.ChatService;

public class AnswerService extends Service {

    private final IBinder binder = new AnswerBinder();
    private ChatService chatService; // ChatService 객체

    @Override
    public void onCreate() {
        super.onCreate();
        chatService = new ChatService(); // ChatService 초기화
    }

    public class AnswerBinder extends Binder {
        public AnswerService getService() {
            return AnswerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    // OpenAI API를 사용한 답변 생성
    public void generateAnswer(String question, boolean TEXTmode ,ChatService.ChatCallback callback) {
        if (question == null || question.trim().isEmpty()) {
            callback.onError("질문이 비어 있습니다.");
            return;
        }

        if (TEXTmode) {chatService.getAnswer(question, callback);}

        else {chatService.getAnswer_emoji(question, callback);}
        //chatService.getAnswer(question, callback);
    }
}
