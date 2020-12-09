/*
 * Copyright (C) 2017-2019 The PixelDust Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.awaken.settings;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.ListPreference;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.search.SearchIndexable;

import com.dirtyunicorns.support.preferences.CustomSeekBarPreference;

@SearchIndexable
public class Gesture extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String KEY_SCREENSHOT_DELAY = "screenshot_gesture_delay";

    private ListPreference mTorchLongPressPowerTimeout;
    private CustomSeekBarPreference mScreenshotDelay;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.miscellaneous);

        final ContentResolver resolver = getContentResolver();

        mScreenshotDelay = (CustomSeekBarPreference) findPreference(KEY_SCREENSHOT_DELAY);
        int value = Settings.System.getIntForUser(resolver,
                KEY_SCREENSHOT_DELAY, 0, UserHandle.USER_CURRENT);
        mScreenshotDelay.setValue(value);
        mScreenshotDelay.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mScreenshotDelay) {
            int value = (Integer) newValue;
            Settings.System.putIntForUser(resolver,
                    KEY_SCREENSHOT_DELAY, value, UserHandle.USER_CURRENT);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.DIRTYTWEAKS;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.miscellaneous);
}
