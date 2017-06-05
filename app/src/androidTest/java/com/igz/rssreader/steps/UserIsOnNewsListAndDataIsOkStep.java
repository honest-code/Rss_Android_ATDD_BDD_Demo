package com.igz.rssreader.steps;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import com.igz.rssreader.pages.NewsPage;
import com.igz.rssreader.support.MockResponseBodyProvider;
import com.igz.rssreader.support.TestProvider;
import com.igz.rssreader.ui.feats.home.HomeActivity;
import com.intelygenz.android.KeyValueKeeper;
import cucumber.api.java.en.Given;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class UserIsOnNewsListAndDataIsOkStep {

	@Rule
	public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

	public UserIsOnNewsListAndDataIsOkStep() throws IOException {
		KeyValueKeeper keyValueKeeper = TestProvider.getKeyValueKeeper();
		MockWebServer mockWebServer = TestProvider.getMockWebServer();
		final HttpUrl baseUrl = mockWebServer.url("/elpais/portada.xml");
		when(keyValueKeeper.load(anyString(), anyString(), any(Class.class))).thenReturn(baseUrl.toString());
		mockWebServer.setDispatcher(new Dispatcher() {
			@Override
			public MockResponse dispatch(RecordedRequest recordedRequest) throws InterruptedException {
				Log.d("PATH", "PATH: " + recordedRequest.getPath());
				if (recordedRequest.getPath()
					.equals("/elpais/portada.xml")) {
					return getMockResponse();
				}
				return null;
			}
		});
	}

	@Given("User is on news list and data is ok")
	public void user_is_on_news_list() {
		activityRule.launchActivity(new Intent());
		SystemClock.sleep(100);
		NewsPage.userIsInNewsList();
	}

	private MockResponse getMockResponse() {
		String body = MockResponseBodyProvider.loadFile("mockresponses/news_list.xml");
		return new MockResponse().setResponseCode(200)
			.setBody(body);
	}

}
