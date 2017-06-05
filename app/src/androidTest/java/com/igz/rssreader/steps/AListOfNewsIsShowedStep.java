package com.igz.rssreader.steps;

import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;
import com.igz.rssreader.pages.NewsPage;
import com.igz.rssreader.support.TestProvider;
import cucumber.api.java.en.Then;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AListOfNewsIsShowedStep {

	private MockWebServer mockWebServer;

	public AListOfNewsIsShowedStep() {
		mockWebServer = TestProvider.getMockWebServer();
	}

	@Then("A list of news is showed")
	public void a_list_of_news_is_showed() throws InterruptedException {
		RecordedRequest request = mockWebServer.takeRequest();
		assertEquals("/elpais/portada.xml", request.getPath());
		SystemClock.sleep(100);
		NewsPage.aListOfNewsIsShowed();
	}

}
