package com.igz.rssreader.steps;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import com.igz.rssreader.core.resource.ResourcesException;
import com.igz.rssreader.core.source.Source;
import com.igz.rssreader.domain.RssItem;
import com.igz.rssreader.resource.RssProvider;
import com.igz.rssreader.support.MockResponseBodyProvider;
import com.igz.rssreader.support.TestProvider;
import com.intelygenz.android.netclient.NetClient;
import com.intelygenz.android.netokclient.NetOkClient;
import cucumber.api.java.en.Given;
import java.io.IOException;
import java.util.List;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class LoadNewsFromNetAndDataIsOkStep {

	private RssProvider rssProvider;

	public LoadNewsFromNetAndDataIsOkStep() throws IOException {
		ContextWrapper contextWrapper = new ContextWrapper(getInstrumentation().getContext()) {
			@Override
			public Context getApplicationContext() {
				return getInstrumentation().getContext();
			}
		};
		NetClient net = new NetOkClient.Builder(contextWrapper).build();
		MockWebServer mockWebServer = TestProvider.getMockWebServer();
		Source source = mock(Source.class);
		rssProvider = new RssProvider(net, source);
		final HttpUrl baseUrl = mockWebServer.url("/elpais/portada.xml");
		when(source.getRssSource()).thenReturn(baseUrl.toString());
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

	@Given("Load news from net and data is ok")
	public void user_is_on_news_list() {
		try {
			List<RssItem> rssItems = rssProvider.loadRssNews("");
			assertNotNull(rssItems);
			assertEquals(13, rssItems.size());
		} catch (ResourcesException e) {
			fail();
		}
	}

	private MockResponse getMockResponse() {
		String body = MockResponseBodyProvider.loadFile("mockresponses/news_list.xml");
		return new MockResponse().setResponseCode(200)
			.setBody(body);
	}

}
