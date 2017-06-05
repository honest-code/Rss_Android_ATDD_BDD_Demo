package com.igz.rssreader;

import com.igz.rssreader.core.resource.Resources;
import com.igz.rssreader.core.resource.ResourcesException;
import com.igz.rssreader.core.source.Source;
import com.igz.rssreader.domain.RssItem;
import com.igz.rssreader.resource.RssProvider;
import com.intelygenz.android.netclient.NetClient;
import com.intelygenz.android.netclient.NetClientException;
import com.intelygenz.android.netclient.Request;
import com.intelygenz.android.netclient.ResponseProcessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResourcesTestCase {

	private NetClient netClient;
	private Resources resources;

	public ResourcesTestCase() {
		netClient = mock(NetClient.class);
		Source source = new Source() {
			@Override
			public String getRssSource() {
				return AppConstants.BASIC_RSS_SOURCE;
			}

			@Override
			public void setRssSource(String newSource) {
			}
		};
		resources = new RssProvider(netClient, source);
	}

	@Test
	public void load_loadRssItems_loaded() {
		List<RssItem> mockItems = new ArrayList<RssItem>() {{
			add(new RssItem("http://lorempixel.com/400/200/sports/1/", "Example Title A", "", "", ""));
			add(new RssItem("http://lorempixel.com/400/200/sports/2/", "Example Title B", "", "", ""));
		}};
		
		try {
			when(netClient.netRequest(any(Request.class), any(ResponseProcessor.class))).thenReturn(mockItems);
			List<RssItem> items = resources.loadRssNews(null);
			assertTrue(items.size() == 2);
		} catch (ResourcesException | NetClientException e) {
			fail();
		}
	}

	@Test
	public void load_filterRssItem_loaded() {
		List<RssItem> mockItems = new ArrayList<RssItem>() {{
			add(new RssItem("http://lorempixel.com/400/200/sports/1/", "Example Title A", "", "", ""));
		}};
		
		try {
			when(netClient.netRequest(any(Request.class), any(ResponseProcessor.class))).thenReturn(mockItems);
			List<RssItem> items = resources.loadRssNews("Example Title A");
			assertTrue(items.size() == 1);
		} catch (ResourcesException | NetClientException e) {
			fail();
		}
	}

	@Test
	public void load_netProblem_exceptionThrown() {
		try {
			NetClientException exception = NetClientException.from(404, "ERROR", new HashMap<String, String>());
			when(netClient.netRequest(any(Request.class), any(ResponseProcessor.class))).thenThrow(exception);
			resources.loadRssNews(null);
			fail();
		} catch (ResourcesException exception) {
			assertNotNull(exception);
		} catch (NetClientException e) {
			fail();
		}
	}

}

