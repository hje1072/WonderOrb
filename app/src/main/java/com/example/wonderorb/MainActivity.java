package com.example.wonderorb;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textViewSpeechResult;
    private Button speechButton;
    private Button visibleButton;
    private SpeechRecognizer speechRecognizer;

    private  asdf;

    private boolean isText1Mode = true; // true: TEXT1 모드, false: TEXT2 모드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        textViewSpeechResult = findViewById(R.id.textViewSpeechResult);
        speechButton = findViewById(R.id.speechButton);
        visibleButton = findViewById(R.id.visibleButton);

        // SpeechRecognizer 초기화
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                textViewSpeechResult.setText("음성을 입력하세요...");
            }

            @Override
            public void onBeginningOfSpeech() {
                textViewSpeechResult.setText("음성 입력 중...");
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // 음성 레벨 변경 (선택적)
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                // 음성 데이터 수신 (선택적)
            }

            @Override
            public void onEndOfSpeech() {
                textViewSpeechResult.setText("입력이 종료되었습니다.");
            }

            @Override
            public void onError(int error) {
                Toast.makeText(MainActivity.this, "에러 발생: " + error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String prefix = isText1Mode ? "TEXT1: " : "TEXT2: "; // 모드에 따라 prefix 설정
                    textViewSpeechResult.setText(prefix + matches.get(0)); // 결과 표시
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                // 실시간 결과 표시 (선택적)
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                // 기타 이벤트 처리 (선택적)
            }
        });

        // 투명 버튼의 터치 이벤트 처리
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
}
