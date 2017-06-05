package com.igz.rssreader.steps;

import android.support.test.runner.AndroidJUnit4;
import com.igz.rssreader.pages.NewsPage;
import cucumber.api.java.en.And;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ServiceResponseInvalidDataStep {

	@And("Service response invalid data")
	public void service_response_invalid_data() {
		NewsPage.anErrorIsShowed();
	}

}
