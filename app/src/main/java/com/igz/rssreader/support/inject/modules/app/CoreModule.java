package com.igz.rssreader.support.inject.modules.app;

import android.os.Handler;
import android.os.Looper;
import com.igz.rssreader.core.resource.Resources;
import com.igz.rssreader.core.source.Source;
import com.igz.rssreader.resource.RssProvider;
import com.igz.rssreader.source.SourceEngine;
import com.intelygenz.android.KeyValueKeeper;
import com.intelygenz.android.netclient.NetClient;
import com.wokdsem.kinject.annotations.Module;
import com.wokdsem.kinject.annotations.Provides;
import com.wokdsem.kommander.Deliverer;
import com.wokdsem.kommander.Kommander;

@Module
class CoreModule {

	@Provides(singleton = true)
	Kommander provideKommander() {
		return Kommander.getInstance(new Deliverer() {
			private Handler handler = new Handler(Looper.getMainLooper());

			@Override
			public void deliver(Runnable runnable) {
				handler.post(runnable);
			}
		});
	}

	@Provides(singleton = true)
	Resources provideResource(NetClient netClient, Source source) {
		return new RssProvider(netClient, source);
	}

	@Provides(singleton = true)
	Source provideSource(KeyValueKeeper keyValueKeeper) {
		return new SourceEngine(keyValueKeeper);
	}

}
