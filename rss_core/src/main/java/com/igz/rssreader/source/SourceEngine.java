package com.igz.rssreader.source;

import com.igz.rssreader.AppConstants;
import com.igz.rssreader.core.source.Source;
import com.intelygenz.android.KeyValueKeeper;

public class SourceEngine implements Source {

	private static final String KEY = "rss";
	private KeyValueKeeper keyValueKeeper;

	public SourceEngine(KeyValueKeeper keyValueKeeper) {
		this.keyValueKeeper = keyValueKeeper;
	}

	@Override
	public String getRssSource() {
		return keyValueKeeper.load(KEY, AppConstants.BASIC_RSS_SOURCE, String.class);
	}

	@Override
	public void setRssSource(String newSource) {
		keyValueKeeper.save(KEY, newSource);
	}

}
