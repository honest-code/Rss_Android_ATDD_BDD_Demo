package com.igz.rssreader.core.resource;

import com.igz.rssreader.domain.RssItem;
import java.util.List;

public interface Resources {

	List<RssItem> loadRssNews(String pattern) throws ResourcesException;

}
