package com.igz.rssreader.ui.feats.home;

import android.os.Bundle;
import com.igz.rssreader.ui.base.ActivityNavigator;
import com.igz.rssreader.ui.base.BaseActivity;
import com.igz.rssreader.ui.base.BaseInteractor;
import com.igz.rssreader.ui.feats.home.news.NewsFragment;

public class HomeActivity extends BaseActivity<HomeView, BaseInteractor> {

	@Override
	protected void onCreated(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			onBoarding();
		}
	}

	private void onBoarding() {
		NewsFragment newsFragment = NewsFragment.newInstance(null);
		viewContextInject(ActivityNavigator.class).navigate(newsFragment, ActivityNavigator.B_REF_SETTINGS);
	}

	@Override
	protected HomeView getView() {
		return new HomeView();
	}

	@Override
	protected BaseInteractor getInteractor() {
		return BaseInteractor.EMPTY_INTERACTOR;
	}

}
