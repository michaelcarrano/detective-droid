package com.michaelcarrano.detectivedroid.presentation.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.michaelcarrano.detectivedroid.R
import com.michaelcarrano.detectivedroid.presentation.ThemeHelper

const val PREF_APP_THEME = "pref_app_theme"

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        val themePreference = findPreference<ListPreference>("pref_app_theme")
        themePreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                ThemeHelper.applyTheme(newValue as String)
                preferenceManager.sharedPreferences.edit()
                    .putString(PREF_APP_THEME, newValue).apply()
                true
            }

        val openSourcePreference = findPreference<Preference>("pref_open_source")
        openSourcePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))
            OssLicensesMenuActivity.setActivityTitle(getString(R.string.pref_title_open_source))
            true
        }
    }
}
