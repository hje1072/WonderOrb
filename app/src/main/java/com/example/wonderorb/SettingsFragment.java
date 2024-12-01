package com.example.wonderorb;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // XML 파일과 연결
        setPreferencesFromResource(R.xml.settings_preferences, rootKey);
    }
}
