package com.example.chat_api;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiKeyProvider {
    public static String getApiKey(Context context) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = context.getAssets().open("apikey.properties");
            properties.load(inputStream);
            return properties.getProperty("API_KEY", ""); // 기본값을 빈 문자열로 설정
        } catch (IOException e) {
            return ""; // 파일을 찾지 못하거나 오류가 발생하면 빈 문자열 반환
        }
    }
}
