package com.igz.rssreader.ui.feats.home.news;

import com.igz.rssreader.core.resource.Resources;
import com.igz.rssreader.core.source.Source;
import com.igz.rssreader.domain.RssItem;
import com.igz.rssreader.ui.base.BaseInteractor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class NewsInteractor extends BaseInteractor {

	boolean setRssSource(final String newSource) {
		inject(Source.class).setRssSource(newSource);
		return true;
	}

	List<RssItem> loadRssNews(final String query) throws Throwable {
		List<RssItem> rssItems = inject(Resources.class).loadRssNews(query);
		sortByDate(rssItems);
		return rssItems;
	}

	private void sortByDate(List<RssItem> rssItems) {
		final SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
		Collections.sort(rssItems, new Comparator<RssItem>() {
			@Override
			public int compare(RssItem lhs, RssItem rhs) {
				try {
					Date date1 = format.parse(lhs.publishDate);
					Date date2 = format.parse(rhs.publishDate);
					return date2.compareTo(date1);
				} catch (ParseException e) {
					return 0;
				}
			}
		});
	}

}
