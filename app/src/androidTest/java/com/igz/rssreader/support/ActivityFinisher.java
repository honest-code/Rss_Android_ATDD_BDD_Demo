package com.igz.rssreader.support;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public final class ActivityFinisher implements Runnable {

	private final ActivityLifecycleMonitor activityLifecycleMonitor;

	private ActivityFinisher() {
		this.activityLifecycleMonitor = ActivityLifecycleMonitorRegistry.getInstance();
	}

	public static void finishOpenActivities() {
		new Handler(Looper.getMainLooper()).post(new ActivityFinisher());
	}

	@Override
	public void run() {
		final List<Activity> activities = new ArrayList<>();
		for (final Stage stage : EnumSet.range(Stage.CREATED, Stage.STOPPED)) {
			activities.addAll(activityLifecycleMonitor.getActivitiesInStage(stage));
		}
		for (final Activity activity : activities) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}
}