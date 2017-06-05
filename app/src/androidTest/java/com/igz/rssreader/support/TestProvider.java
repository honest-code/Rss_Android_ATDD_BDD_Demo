package com.igz.rssreader.support;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import com.intelygenz.android.KeyValueKeeper;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import java.io.IOException;
import java.util.Collection;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.runner.RunWith;

import static android.support.test.runner.lifecycle.Stage.RESUMED;

@RunWith(AndroidJUnit4.class)
public class TestProvider {

	private static TestToolkit testToolkit;

	public TestProvider() {
		testToolkit = new TestToolkit();
	}

	public static KeyValueKeeper getKeyValueKeeper() {
		return testToolkit.keyValueKeeper;
	}

	public static MockWebServer getMockWebServer() {
		return testToolkit.mockWebServer;
	}

	public static Activity getCurrentActivity() {
		final Activity[] activity = new Activity[1];
		InstrumentationRegistry.getInstrumentation()
			.runOnMainSync(new Runnable() {
				public void run() {
					Activity currentActivity = null;
					Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance()
						.getActivitiesInStage(RESUMED);
					if (resumedActivities.iterator()
						.hasNext()) {
						currentActivity = (Activity) resumedActivities.iterator()
							.next();
						activity[0] = currentActivity;
					}
				}
			});
		return activity[0];
	}

	@Before
	public void setUp() throws IOException {
		testToolkit.setUp();
	}

	@After
	public void tearDown() throws IOException {
		testToolkit.tearDown();
	}

}
