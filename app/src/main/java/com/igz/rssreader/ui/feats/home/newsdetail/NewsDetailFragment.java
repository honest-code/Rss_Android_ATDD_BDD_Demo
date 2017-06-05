package com.igz.rssreader.ui.feats.home.newsdetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.igz.rssreader.domain.RssItem;
import com.igz.rssreader.ui.base.ActivityNavigator;
import com.igz.rssreader.ui.base.BaseFragment;
import com.igz.rssreader.ui.base.BaseInteractor;

import static com.igz.rssreader.support.ui.NotParcelled.fromNotParcelled;
import static com.igz.rssreader.support.ui.NotParcelled.toNotParcelled;

public class NewsDetailFragment extends BaseFragment<NewsDetailView, BaseInteractor> {

	private static final String ARGS_KEY = "RSS_ITEM";
	private RssItem item;

	public static NewsDetailFragment newInstance(RssItem rssItem) {
		NewsDetailFragment detailFragment = new NewsDetailFragment();
		Bundle args = new Bundle();
		args.putString(ARGS_KEY, toNotParcelled(rssItem));
		detailFragment.setArguments(args);
		return detailFragment;
	}

	@Override
	protected void loadArguments(Bundle arguments) {
		item = fromNotParcelled(arguments.getString(ARGS_KEY), RssItem.class);
	}

	@Override
	protected NewsDetailView getFragmentView() {
		return new NewsDetailView(item.title, new NewsDetailView.OnNewsDetailListener() {
			@Override
			public void onBack() {
				viewContextInject(ActivityNavigator.class).navigateBack();
			}

			@Override
			public void onShowInBrowser(String link) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(link));
				getActivity().startActivity(browserIntent);
			}
		});
	}

	@Override
	protected BaseInteractor getInteractor() {
		return BaseInteractor.EMPTY_INTERACTOR;
	}

	@Override
	public void onViewCreated() {
		fragmentView.paintRss(item);
	}

}
