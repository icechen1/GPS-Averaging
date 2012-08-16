package org.destil.gpsaveraging;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Settings for units and coordinate format. It is using deprecated
 * PreferenceScreen, because modern fragment-based version is not part of
 * compatibility library.
 * 
 * @author Destil
 */
public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private static final String UNITS = "UNITS";
	private static final String COORDINATE_FORMAT = "COORDINATE_FORMAT";
	public static final String UNITS_METRIC = "metric";
	public static final String UNITS_IMPERIAL = "imperial";
	private static final String UNITS_DEFAULT_VALUE = UNITS_METRIC;
	private static final String[] UNITS_VALUES = { UNITS_METRIC, UNITS_IMPERIAL };
	private static String[] UNITS_OPTIONS;
	public static final String COORDS_DECIMAL = "decimal";
	public static final String COORDS_MINUTES = "minutes";
	public static final String COORDS_SECONDS = "seconds";
	private static final String COORDS_DEFAULT_VALUE = COORDS_MINUTES;
	private static final String[] COORDS_VALUES = { COORDS_DECIMAL, COORDS_MINUTES, COORDS_SECONDS };
	private static final String[] COORDS_OPTIONS = { "dd.ddddd", "N dd° mm.mmm'", "N dd° mm' ss.sss''" };

	// UI elements
	private ListPreference uiUnits;
	private ListPreference uiCoordinateFormat;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO: getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		addPreferencesFromResource(R.xml.activity_preferences);
		uiUnits = (ListPreference) findPreference(UNITS);
		uiCoordinateFormat = (ListPreference) findPreference(COORDINATE_FORMAT);
		init();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences preferences = getPreferenceScreen().getSharedPreferences();
		preferences.registerOnSharedPreferenceChangeListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
		if (key.equals(UNITS)) {
			if (uiUnits.getEntry() != null) {
				uiUnits.setSummary(uiUnits.getEntry());
			}
		} else if (key.equals(COORDINATE_FORMAT)) {
			if (uiCoordinateFormat.getEntry() != null) {
				uiCoordinateFormat.setSummary(uiCoordinateFormat.getEntry());
			}
		}
	}

	/**
	 * Returns preferred unit format.
	 */
	public static String getUnitsFormat(Context c) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
		return prefs.getString(UNITS, UNITS_DEFAULT_VALUE);
	}

	/**
	 * Returns preferred coordinate format.
	 */
	public static String getCoordinateFormat(Context c) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
		return prefs.getString(COORDINATE_FORMAT, COORDS_DEFAULT_VALUE);
	}

	/**
	 * Inits settings according to constants.
	 */
	private void init() {
		// units
		uiUnits.setEntryValues(UNITS_VALUES);
		UNITS_OPTIONS = new String[] { getString(R.string.metric), getString(R.string.imperial) };
		uiUnits.setEntries(UNITS_OPTIONS);
		uiUnits.setDefaultValue(UNITS_DEFAULT_VALUE);
		uiUnits.setSummary(UNITS_OPTIONS[0]);
		onSharedPreferenceChanged(null, UNITS);
		// coordinate formats
		uiCoordinateFormat.setEntryValues(COORDS_VALUES);
		uiCoordinateFormat.setEntries(COORDS_OPTIONS);
		uiCoordinateFormat.setDefaultValue(COORDS_DEFAULT_VALUE);
		uiCoordinateFormat.setSummary(COORDS_OPTIONS[1]);
		onSharedPreferenceChanged(null, COORDINATE_FORMAT);
	}
}