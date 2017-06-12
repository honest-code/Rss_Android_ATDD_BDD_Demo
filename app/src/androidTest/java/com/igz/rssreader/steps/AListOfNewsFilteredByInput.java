package com.igz.rssreader.steps;

import android.support.test.runner.AndroidJUnit4;
import com.igz.rssreader.pages.NewsDetailPage;
import cucumber.api.java.en.Then;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AListOfNewsFilteredByInput {

	@Then("a list of news filtered by input")
	public void a_detail_screen_is_showed() throws InterruptedException {
		NewsDetailPage.aDetailScreenIsShowed();
	}

}
