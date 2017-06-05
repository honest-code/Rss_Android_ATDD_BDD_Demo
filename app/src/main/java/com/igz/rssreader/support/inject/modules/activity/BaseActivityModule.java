package com.igz.rssreader.support.inject.modules.activity;

import android.app.Activity;
import android.content.Context;
import com.igz.rssreader.ui.base.ActivityNavigator;
import com.igz.rssreader.ui.base.BaseActivity;
import com.igz.rssreader.ui.base.ViewNavigator;
import com.wokdsem.kinject.annotations.Module;
import com.wokdsem.kinject.annotations.Provides;

@Module(completed = true)
public class BaseActivityModule {

	private final BaseActivity baseActivity;
	private final ActivityNavigator navigator;

	public BaseActivityModule(BaseActivity baseActivity) {
		this.baseActivity = baseActivity;
		this.navigator = new ActivityNavigator(baseActivity);
	}

	@Provides
	public Context provideContext() {
		return baseActivity.getApplicationContext();
	}

	@Provides
	public Activity provideActivity() {
		return baseActivity;
	}

	@Provides
	public BaseActivity provideBaseActivity() {
		return baseActivity;
	}

	@Provides
	public ActivityNavigator provideActivityNavigator() {
		return navigator;
	}

	@Provides(singleton = true)
	public ViewNavigator provideViewNavigator(BaseActivity baseActivity, ActivityNavigator navigator) {
		return new ViewNavigator(baseActivity, navigator);
	}

}
