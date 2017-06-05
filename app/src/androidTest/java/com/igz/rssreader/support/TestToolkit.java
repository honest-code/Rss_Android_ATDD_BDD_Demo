package com.igz.rssreader.support;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.igz.rssreader.RssReaderApp;
import com.igz.rssreader.instrument.AppInjector;
import com.igz.rssreader.support.inject.KinjectInjector;
import com.igz.rssreader.support.inject.modules.app.AppModule;
import com.igz.rssreader.support.inject.modules.app.DepModule;
import com.intelygenz.android.KeyValueKeeper;
import java.io.IOException;
import okhttp3.mockwebserver.MockWebServer;
import org.mockito.Mockito;

import static com.igz.rssreader.support.inject.modules.app.AppModuleMapper.from;
import static org.mockito.Mockito.mock;

class TestToolkit {

	private final RssReaderApp app;

	MockWebServer mockWebServer;
	KeyValueKeeper keyValueKeeper;

	TestToolkit() {
		Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
		app = (RssReaderApp) instrumentation.getTargetContext()
			.getApplicationContext();
		keyValueKeeper = mock(KeyValueKeeper.class);
		mockWebServer = new MockWebServer();
	}

	void setUp() throws IOException {
		mockWebServer.start();
		AppInjector.initInjector(new KinjectInjector(from(getAppModule())));
	}

	void tearDown() throws IOException {
		AppInjector.initInjector(null);
		mockWebServer.shutdown();
		mockWebServer = null;
		Mockito.reset(keyValueKeeper);
		ActivityFinisher.finishOpenActivities();
	}

	private AppModule getAppModule() {
		return new AppModule(app) {
			@Override
			public DepModule includeDepModule() {
				return new DepModule() {
					@Override
					public KeyValueKeeper provideKvKeeper(Context context) {
						return keyValueKeeper;
					}
				};
			}
		};
	}

}
