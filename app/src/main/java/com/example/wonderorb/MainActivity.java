package com.example.wonderorb;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chat_api.ApiKeyProvider;
import com.example.chat_api.ChatService;
import com.example.chat_api.RetrofitClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FrameLayout overlay;
    private TextView textViewSpeechResult;
    private Button speechButton;
    private ImageButton visibleButton;
    private SpeechRecognizer speechRecognizer;
    private FrameLayout notification_background;


    private boolean isText1Mode = true; // true: TEXT1 모드, false: TEXT2 모드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = ApiKeyProvider.getApiKey(this);
        if (apiKey.isEmpty() || apiKey.equals("sk-YOUR_ACTUAL_API_KEY")) {
            Toast.makeText(this, "올바른 API 키를 설정하세요.", Toast.LENGTH_LONG).show();
        } else {
            RetrofitClient.setApiKey(apiKey);
        }



        // UI 요소 초기화
        overlay = findViewById(R.id.overlay);
        textViewSpeechResult = findViewById(R.id.textViewSpeechResult);
        speechButton = findViewById(R.id.speechButton);
        visibleButton = findViewById(R.id.visibleButton);
        notification_background = findViewById(R.id.notification_background);

        // SpeechRecognizer 초기화
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                //초기상태
                textViewSpeechResult.setText("음성을 입력하세요...");
            }

            @Override
            public void onBeginningOfSpeech() {
                //입력받는 상태
                textViewSpeechResult.setText("음성 입력 중...");
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // 음성 레벨 변경 (선택적) 사용X
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                // 음성 데이터 수신 (선택적) 사용X
            }

            @Override
            public void onEndOfSpeech() {
                //입력완료상태
                textViewSpeechResult.setText("입력이 종료되었습니다.");
            }

            @Override
            public void onError(int error) {
                Toast.makeText(MainActivity.this, "에러 발생: " + error, Toast.LENGTH_SHORT).show();

                // 에러가 7 일 경우 음성인식이 안들어왔을 경우임. 이는 자주 발생할 수 있는 에러이므로 처리 자세히할것.
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {

                    String question = matches.get(0).trim(); // 인식된 첫 번째 문장을 질문으로 사용

                    if (isServiceBound) {
                        if (!question.isEmpty()) {
                            // AnswerService를 통해 AI 기반 답변 받기
                            answerService.generateAnswer(question, new ChatService.ChatCallback() {
                                @Override
                                public void onSuccess(String answer) {
                                    textViewSpeechResult.setText(answer); // 답변 표시
                                    runAnimation(textViewSpeechResult);  // 답변 표시 후 애니메이션 실행
                                }

                                @Override
                                public void onError(String error) {
                                    textViewSpeechResult.setText(error); // 오류 메시지 표시
                                }
                            });
                        } else {
                            textViewSpeechResult.setText("질문이 비어 있습니다.");
                        }
                    } else {
                        textViewSpeechResult.setText("서비스에 연결되지 않았습니다.");
                    }
                }
            }


            @Override
            public void onPartialResults(Bundle partialResults) {
                // 실시간 결과 표시 (선택적) 사용X
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                // 기타 이벤트 처리 (선택적) 사용X
            }
        });


        /* 투명 버튼의 터치 이벤트 처리 */
        speechButton.setOnTouchListener((View v, MotionEvent event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startSpeechRecognition();
                    return true;
                case MotionEvent.ACTION_UP:
                    stopSpeechRecognition();
                    return true;
            }
            return false;
        });


        // visibleButton 클릭 이벤트 처리
        visibleButton.setOnClickListener(v -> {
            isText1Mode = !isText1Mode; // 모드 전환

            //배경도 전환
            int background = isText1Mode ? R.drawable.background_purple :  R.drawable.background_blue;

            runAnimation(overlay);
            notification_background.setBackgroundResource(background);

            String modeMessage = isText1Mode ? "TEXT1 모드로 전환되었습니다." : "TEXT2 모드로 전환되었습니다.";
            Toast.makeText(this, modeMessage, Toast.LENGTH_SHORT).show();
        });



    }

    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR"); // 기본 언어: 한국어
        speechRecognizer.startListening(intent);
    }

    private void stopSpeechRecognition() {
        if (speechRecognizer != null) {
            speechRecognizer.stopListening();
            Toast.makeText(this, "음성 입력을 종료했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy(); // 메모리 초기화
        }
    }


    //결과 버튼의 애니메이션 이벤트처리.
    private void runAnimation(TextView textView) {
        // 불투명해지는 애니메이션 (Alpha: 0 -> 1)
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f);
        fadeIn.setDuration(1000); // 1초 동안 불투명해짐

        // 불투명 상태 유지 (Alpha: 1 -> 1)
        ObjectAnimator hold = ObjectAnimator.ofFloat(textView, "alpha", 1f, 1f);
        hold.setDuration(1500); // 1.5초 동안 유지

        // 투명해지는 애니메이션 (Alpha: 1 -> 0)
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f);
        fadeOut.setDuration(500); // 0.5초 동안 투명해짐

        // AnimatorSet으로 애니메이션 조합
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(fadeIn, hold, fadeOut);
        animatorSet.start();
    }


    //화면전환 이벤트
    private void runAnimation(View View) {

        // 투명해지는 애니메이션 (Alpha: 1 -> 0)
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(View, "alpha", 1f, 0f);
        fadeOut.setDuration(500); // 0.5초 동안 투명해짐

        // AnimatorSet으로 애니메이션 조합
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(fadeOut);
        animatorSet.start();

    }



    //서비스를 관리하는 부분. 질의응답 서비스.

    private AnswerService answerService; // AnswerService 객체
    private boolean isServiceBound = false; // Service 연결 상태

    // Service 연결 콜백
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AnswerService.AnswerBinder binder = (AnswerService.AnswerBinder) service;
            answerService = binder.getService(); // Service 객체 가져오기
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceBound = false; // 연결 끊김 상태
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        // Service 바인딩
        Intent intent = new Intent(this, AnswerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Service 언바인딩
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }
}
