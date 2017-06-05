package com.igz.rssreader;

import android.app.Application;
import com.igz.rssreader.instrument.AppInjector;
import com.igz.rssreader.support.inject.KinjectInjector;
import com.igz.rssreader.support.inject.modules.app.AppModule;

import static com.igz.rssreader.support.inject.modules.app.AppModuleMapper.from;

public class RssReaderApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		setUpInjector();
	}

	protected void setUpInjector() {
		AppModule appModule = new AppModule(this);
		AppInjector.initInjector(new KinjectInjector(from(appModule)));
	}

}
