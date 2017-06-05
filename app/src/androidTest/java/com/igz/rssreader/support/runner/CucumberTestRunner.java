package com.igz.rssreader.support.runner;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;
import android.util.Log;
import com.igz.rssreader.BuildConfig;
import com.igz.rssreader.support.GrantPermissions;
import com.igz.rssreader.support.TestApp;
import cucumber.api.android.CucumberInstrumentationCore;

public class CucumberTestRunner extends AndroidJUnitRunner {

	public static final String TAG = CucumberTestRunner.class.getSimpleName();

	/**
	 * This is the item Cucumber uses to identify the tags parameter, see method
	 * cucumber-android-1.2.2.jar\cucumber\runtime\android\Arguments.class @ getCucumberOptionsString()
	 */
	private static final String CUCUMBER_TAGS_KEY = "tags";
	private final CucumberInstrumentationCore instrumentationCore = new CucumberInstrumentationCore(this);

	@Override
	public void onCreate(final Bundle bundle) {
		super.onCreate(bundle);
		// Read tags passed as parameters and overwrite @CucumberOptions tags inside CucumberTestCase.java
		final String tags = BuildConfig.TEST_TAGS;
		if (!tags.isEmpty()) {
			// Reformat tags list to separate items with '--' as expected by Cucumber library, see method
			// cucumber-android-1.2.2.jar\cucumber\runtime\android\Arguments.class @ appendOption()
			bundle.putString(CUCUMBER_TAGS_KEY, tags.replaceAll(",", "--")
				.replaceAll("\\s", ""));
		}
		instrumentationCore.create(bundle);
	}

	@Override
	public void onStart() {
		waitForIdleSync();
		GrantPermissions.grant();
		instrumentationCore.start();
	}

	@Override
	public Application newApplication(ClassLoader cl, String className, Context context)
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Log.d("TESTING", "newApplication");
		return super.newApplication(cl, TestApp.class.getName(), context);
	}

}