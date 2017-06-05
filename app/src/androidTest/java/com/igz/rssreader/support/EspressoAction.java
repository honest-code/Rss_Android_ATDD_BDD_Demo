package com.igz.rssreader.support;

import android.support.test.espresso.matcher.ViewMatchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspressoAction {

	public static void checkViewIsDisplay(int id) {
		onView(withId(id)).check(matches(isDisplayed()));
	}

	public static void checkViewHasVisibility(int viewId, ViewMatchers.Visibility visibility) {
		onView(withId(viewId)).check(matches(withEffectiveVisibility(visibility)));
	}

	public static void checkViewIsEnable(int viewId) {
		onView(withId(viewId)).check(matches(isEnabled()));
	}

	public static void performClick(int viewId) {
		onView(withId(viewId)).perform(click());
	}

	public static void typeTextOnView(int viewId, String value) {
		onView(withId(viewId)).perform(clearText(), typeText(value));
	}

	public static void typeTextOnView(int viewId, int value) {
		onView(withId(viewId)).perform(clearText(), typeText(String.valueOf(value)));
	}

	public void swipeToLeft(int viewId) {
		onView(withId(viewId)).perform(swipeLeft());
	}

	public void swipeToRight(int viewId) {
		onView(withId(viewId)).perform(swipeRight());
	}

}