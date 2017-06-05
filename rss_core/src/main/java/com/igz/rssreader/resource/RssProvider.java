package com.igz.rssreader.resource;

import com.igz.rssreader.core.resource.Resources;
import com.igz.rssreader.core.resource.ResourcesException;
import com.igz.rssreader.core.resource.RssParserException;
import com.igz.rssreader.core.source.Source;
import com.igz.rssreader.domain.RssItem;
import com.intelygenz.android.netclient.NetClient;
import com.intelygenz.android.netclient.NetClientException;
import com.intelygenz.android.netclient.Request;
import com.intelygenz.android.netclient.Response;
import com.intelygenz.android.netclient.ResponseProcessor;
import java.util.List;

public class RssProvider implements Resources {

	private NetClient netClient;
	private Source source;

	public RssProvider(NetClient netClient, Source source) {
		this.netClient = netClient;
		this.source = source;
	}

	@Override
	public List<RssItem> loadRssNews(final String pattern) throws ResourcesException {
		String url = source.getRssSource();
		try {
			Request request = new Request.Builder(url).build();
			return netClient.netRequest(request, new RssXmlResponseProcessor(pattern));
		} catch (NetClientException e) {
			throw new ResourcesException(e);
		}
	}

	private class RssXmlResponseProcessor implements ResponseProcessor<List<RssItem>> {

		private final String pattern;

		RssXmlResponseProcessor(String pattern) {
			this.pattern = pattern;
		}
		
		@Override
		public List<RssItem> process(Response response) throws NetClientException {
			try {
				return RssParser.parseRss(response.message, pattern);
			} catch (RssParserException e) {
				throw NetClientException.from(e);
			}
		}
	}

}
