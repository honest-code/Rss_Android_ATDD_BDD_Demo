package com.igz.rssreader.actions;

import android.support.test.espresso.matcher.ViewMatchers;
import com.igz.rssreader.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static com.igz.rssreader.support.matchers.RecyclerViewMatcher.withRecyclerView;
import static org.hamcrest.Matchers.not;

public class NewsAction {

	public static void toolbarIsVisible() {
		onView(withId(R.id.news_detail_fragment_toolbar)).check(matches(isDisplayed()));
	}

	public static void errorTextViewNotVisible() {
		onView(withId(R.id.news_fragment_info_textview)).check(
			matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
		onView(withId(R.id.news_fragment_info_textview)).check(matches(withText("")));
	}

	public static void errorTextViewWithError() {
		onView(withId(R.id.news_fragment_info_textview)).check(
			matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
		onView(withId(R.id.news_fragment_info_textview)).check(matches(withText(R.string.news_loading_error)));
	}

	public static void newsListVisible() {
		onView(withId(R.id.news_detail_fragment_toolbar)).check(matches(isDisplayed()));
	}

	public static void newsIsVisible() {
		onView(withRecyclerView(R.id.news_fragment_recyclerview).atPosition(0)).check(matches(isDisplayed()));
	}

	public static void newsIsNotVisible() {
		onView(withId(R.id.news_fragment_recyclerview)).check(
			matches(not(hasDescendant(withId(R.id.news_holder_component_cardview)))));
	}

	public static void performClickAtRecyclerPosition(int itemPosition) {
		onView(withRecyclerView(R.id.news_fragment_recyclerview).atPosition(itemPosition)).perform(click());
	}
	
	public static void userInputKey(String inputKey) {
		throw new IllegalStateException("Not implement");
	}
}
