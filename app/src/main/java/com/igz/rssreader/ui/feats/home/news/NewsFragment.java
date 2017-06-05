package com.igz.rssreader.ui.feats.home.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.igz.rssreader.domain.RssItem;
import com.igz.rssreader.ui.base.ActivityNavigator;
import com.igz.rssreader.ui.base.BaseFragment;
import com.igz.rssreader.ui.feats.home.HomeActivity;
import com.igz.rssreader.ui.feats.home.newsdetail.NewsDetailFragment;
import com.wokdsem.kommander.Action;
import com.wokdsem.kommander.Response;
import java.util.List;

public class NewsFragment extends BaseFragment<NewsView, NewsInteractor> {
	
	private static final String QUERY = "QUERY";
	private String query;
	
	private List<RssItem> news;
	
	public static NewsFragment newInstance(String query) {
		NewsFragment newsFragment = new NewsFragment();
		Bundle args = new Bundle();
		args.putString(QUERY, query);
		newsFragment.setArguments(args);
		return newsFragment;
	}
	
	@Override
	protected void loadArguments(Bundle arguments) {
		query = arguments.getString(QUERY);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(query == null);
	}
	
	@Override
	protected NewsView getFragmentView() {
		return new NewsView(query, new NewsView.ViewListener() {
			@Override
			public void onQuery(String query) {
				NewsFragment queryFragment = NewsFragment.newInstance(query);
				viewContextInject(ActivityNavigator.class).navigate(queryFragment);
			}
			
			@Override
			public void onSourceUpdated(String newSource) {
				updateSource(newSource);
			}
			
			@Override
			public void onRssSelected(RssItem rssItem) {
				NewsDetailFragment detailFragment = NewsDetailFragment.newInstance(rssItem);
				viewContextInject(ActivityNavigator.class).navigate(detailFragment);
			}
		});
	}
	
	@Override
	protected NewsInteractor getInteractor() {
		return new NewsInteractor();
	}
	
	@Override
	protected void onViewCreated() {
		loadNews();
	}
	
	private void sourceUpdated(Boolean response) {
		Context context = viewContextInject(Context.class);
		Intent intent = new Intent(context, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	private void updateSource(final String newSource) {
		makeKommand(new Action<Boolean>() {
			@Override
			public Boolean action() throws Throwable {
				return interactor.setRssSource(newSource);
			}
		})
			.setOnCompleted(new Response.OnCompleted<Boolean>() {
				@Override
				public void onCompleted(Boolean response) {
					sourceUpdated(response);
				}
			})
			.kommand(kommandTokenBox, TAG_VIEW);
	}
	
	private void loadNews() {
		if (news == null) {
			fragmentView.showProgress();
			makeKommand(new Action<List<RssItem>>() {
				@Override
				public List<RssItem> action() throws Throwable {
					return interactor.loadRssNews(query);
				}
			})
				.setOnCompleted(new Response.OnCompleted<List<RssItem>>() {
					@Override
					public void onCompleted(List<RssItem> news) {
						loadNewsCompleted(news);
					}
				})
				.setOnError(new Response.OnError() {
					@Override
					public void onError(Throwable e) {
						loadNewsError(e);
					}
				})
				.kommand(kommandTokenBox, TAG_VIEW);
		} else {
			fragmentView.paintNews(news);
		}
	}
	
	private void loadNewsCompleted(List<RssItem> news) {
		NewsFragment.this.news = news;
		fragmentView.paintNews(news);
	}
	
	private void loadNewsError(Throwable e) {
		fragmentView.showError();
	}
	
}
