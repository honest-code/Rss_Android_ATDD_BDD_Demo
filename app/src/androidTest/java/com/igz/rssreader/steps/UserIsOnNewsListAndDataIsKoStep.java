package com.igz.rssreader.steps;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.igz.rssreader.pages.NewsPage;
import com.igz.rssreader.support.TestProvider;
import com.igz.rssreader.ui.feats.home.HomeActivity;
import com.intelygenz.android.KeyValueKeeper;
import cucumber.api.java.en.Given;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class UserIsOnNewsListAndDataIsKoStep {

	@Rule
	public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

	public UserIsOnNewsListAndDataIsKoStep() {
		KeyValueKeeper keyValueKeeper = TestProvider.getKeyValueKeeper();
		MockWebServer mockWebServer = TestProvider.getMockWebServer();
		HttpUrl baseUrl = mockWebServer.url("/elpais/portada.xml");
		mockWebServer.enqueue(getMockErrorResponse());
		when(keyValueKeeper.load(anyString(), anyString(), any(Class.class))).thenReturn(baseUrl.toString());
	}

	@Given("User is on news list and data is ko")
	public void user_is_on_news_list() {
		activityRule.launchActivity(new Intent());
		NewsPage.userIsInNewsList();
	}

	private MockResponse getMockErrorResponse() {
		return new MockResponse().setResponseCode(400)
			.setBody("error");
	}

}
