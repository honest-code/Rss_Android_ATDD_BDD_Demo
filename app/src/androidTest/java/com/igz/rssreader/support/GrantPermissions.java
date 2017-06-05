package com.igz.rssreader.support;

import android.app.UiAutomation;
import java.util.Locale;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;

public class GrantPermissions {

	private static final String[] perms = { "android.permission.READ_CONTACTS", "android.permission.CAMERA" };
	private static final String SHELL_SCRIPT_TEMPLATE = "pm grant %s %s";

	public static void grant() {
		UiAutomation uiAutomation = getInstrumentation().getUiAutomation();
		String packageName = getTargetContext().getPackageName();
		for (String perm : perms) {
			uiAutomation.executeShellCommand(String.format(Locale.US, SHELL_SCRIPT_TEMPLATE, packageName, perm));
		}
	}

}
