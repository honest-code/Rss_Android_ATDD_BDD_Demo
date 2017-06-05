package com.igz.rssreader.steps;

import android.support.test.runner.AndroidJUnit4;
import com.igz.rssreader.pages.NewsPage;
import cucumber.api.java.en.Then;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserClickOnAValidNewsInTheList {

	@Then("User click on a valid news in the list")
	public void user_click_on_a_valid_news_in_the_list() throws InterruptedException {
		NewsPage.userClickInNewsAValidNews();
	}

}
