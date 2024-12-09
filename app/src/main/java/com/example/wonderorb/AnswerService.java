package com.example.wonderorb;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

// Service 클래스 정의
public class AnswerService extends Service {

    // 바인더 객체
    private final IBinder binder = new AnswerBinder();

    // 바인더 클래스 정의
    public class AnswerBinder extends Binder {
        public AnswerService getService() {
            return AnswerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    // 간단한 답변 생성 메서드
    public String generateAnswer(String question) {
        if (question.equalsIgnoreCase("hello")) {
            return "안녕하세요! 무엇을 도와드릴까요?";
        } else if (question.equalsIgnoreCase("???")) {
            return "오늘 날씨는 맑습니다.";
        } else {
            //return "질문에 대한 답변을 찾을 수 없습니다.";
            return question; //우선 그냥 받아치기.
        }
    }
}
