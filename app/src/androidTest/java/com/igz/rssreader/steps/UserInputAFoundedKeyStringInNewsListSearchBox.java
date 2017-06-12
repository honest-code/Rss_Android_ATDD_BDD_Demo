package com.igz.rssreader.steps;

import android.support.test.runner.AndroidJUnit4;
import com.igz.rssreader.pages.NewsPage;
import cucumber.api.java.en.When;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserInputAFoundedKeyStringInNewsListSearchBox {
	
	@When("user input a founded key string in news list search box")
	public void user_input_a_founded_key() {
		NewsPage.userInputKey("diplomacia");
	}
	
}
