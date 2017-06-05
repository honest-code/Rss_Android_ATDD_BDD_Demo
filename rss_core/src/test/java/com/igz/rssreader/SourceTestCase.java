package com.igz.rssreader;

import com.igz.rssreader.core.source.Source;
import com.igz.rssreader.source.SourceEngine;
import com.intelygenz.android.KeyValueKeeper;
import com.intelygenz.android.kvkFake.KvkFake;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SourceTestCase {

	private Source source;

	@Before
	public void initKeeper() {
		KeyValueKeeper keeper = new KvkFake();
		source = new SourceEngine(keeper);
	}

	@Test
	public void recover_defaultValue_recovered() {
		String rssSource = source.getRssSource();
		assertEquals(rssSource, AppConstants.BASIC_RSS_SOURCE);
	}

	@Test
	public void recover_recoverSetValue_recovered() {
		String sourceValue = "TEST_VALUE";
		source.setRssSource(sourceValue);
		String rssSource = source.getRssSource();
		assertEquals(rssSource, sourceValue);
	}

}
