package com.igz.rssreader.steps;

import android.support.test.runner.AndroidJUnit4;
import com.igz.rssreader.pages.NewsPage;
import com.igz.rssreader.support.TestProvider;
import cucumber.api.java.en.Then;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AConnectivityErrorShowedStep {

	private MockWebServer mockWebServer;

	public AConnectivityErrorShowedStep() {
		mockWebServer = TestProvider.getMockWebServer();
	}

	@Then("A connectivity error showed")
	public void a_connectivity_error_showed() throws InterruptedException {
		RecordedRequest request = mockWebServer.takeRequest();
		assertEquals("/elpais/portada.xml", request.getPath());
		NewsPage.anErrorIsShowed();
	}

}
