package com.example.wonderorb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.gotev.speech.Speech;
import net.gotev.speech.SpeechDelegate;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SpeechDelegate {

    private Button speechButton;
    private TextToSpeech textToSpeech;
    private Locale currentLocale = Locale.KOREAN; // 기본 언어 설정: 한국어

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // gotev Speech 초기화
        Speech.init(this, getPackageName());

        // TTS 초기화
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(currentLocale);
            } else {
                Toast.makeText(this, "TTS Initialization failed", Toast.LENGTH_SHORT).show();
            }
        });

        // 음성 인식 버튼 초기화
        speechButton = findViewById(R.id.speechButton);
        speechButton.setOnClickListener(v -> {
            try {
                Speech.getInstance().startListening(this);
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // 설정 버튼 초기화
        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLanguageFromPreferences(); // Activity가 다시 표시될 때 언어 업데이트
    }

    private void updateLanguageFromPreferences() {
        // SharedPreferences에서 언어 값 읽기
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String language = prefs.getString("language_preference", "en"); // 기본값 영어

        // 언어 설정에 따라 Locale 업데이트
        currentLocale = "ko".equals(language) ? Locale.KOREAN : Locale.US;
        updateLanguage();
    }

    private void updateLanguage() {
        Speech.getInstance().setLocale(currentLocale);
        if (textToSpeech != null) {
            textToSpeech.setLanguage(currentLocale);
        }
        Toast.makeText(this, "Language updated to: " + (currentLocale == Locale.KOREAN ? "한국어" : "English"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartOfSpeech() {
        Toast.makeText(this, "음성 입력을 시작합니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSpeechPartialResults(List<String> results) {
        if (!results.isEmpty()) {
            Toast.makeText(this, "중간 결과: " + results.get(0), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSpeechResult(String result) {
        if (result.isEmpty()) {
            Toast.makeText(this, "음성이 감지되지 않았습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "결과: " + result, Toast.LENGTH_LONG).show();
            speakText(result);
        }
    }

    @Override
    public void onSpeechRmsChanged(float rmsdB) {
        // 음성 입력 중 RMS 데시벨 값 변화 (필요 없으면 비워둘 수 있음)
    }

    private void speakText(String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        Speech.getInstance().shutdown();
        super.onDestroy();
    }
}
