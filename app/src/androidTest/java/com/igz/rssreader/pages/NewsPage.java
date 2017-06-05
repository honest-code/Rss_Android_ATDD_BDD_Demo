package com.igz.rssreader.pages;

import com.igz.rssreader.actions.NewsAction;

public class NewsPage {

	public static void aListOfNewsIsShowed() {
		NewsAction.toolbarIsVisible();
		NewsAction.errorTextViewNotVisible();
		NewsAction.newsIsVisible();
	}

	public static void anErrorIsShowed() {
		NewsAction.toolbarIsVisible();
		NewsAction.errorTextViewWithError();
		NewsAction.newsIsNotVisible();
	}

	public static void userIsInNewsList() {
		NewsAction.newsListVisible();
	}
	
	public static void userInputKey(String inputKey) {
		NewsAction.userInputKey(inputKey);
	}

	public static void userClickInNewsAValidNews() {
		NewsAction.performClickAtRecyclerPosition(2);
	}

}
