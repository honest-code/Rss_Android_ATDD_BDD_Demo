package com.igz.rssreader.actions;

import com.igz.rssreader.R;
import com.igz.rssreader.support.EspressoAction;

public class NewsDetailAction {
	
	public static void viewIsVisible() {
		EspressoAction.checkViewIsDisplay(R.id.news_detail_fragment_toolbar);
	}

}
