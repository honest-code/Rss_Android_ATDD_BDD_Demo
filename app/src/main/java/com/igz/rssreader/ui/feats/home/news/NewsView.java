package com.igz.rssreader.ui.feats.home.news;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.igz.rssreader.R;
import com.igz.rssreader.domain.RssItem;
import com.igz.rssreader.support.ui.TextViewUtils;
import com.igz.rssreader.ui.base.BaseFragmentView;
import java.util.List;

class NewsView extends BaseFragmentView {
	
	private final String query;
	private final ViewListener listener;
	private TextView newsInfo;
	private ProgressBar progressBar;
	private NewsRenderer renderer;
	
	NewsView(String query, ViewListener listener) {
		super(R.layout.news_fragment);
		this.listener = listener;
		this.query = query;
	}
	
	@Override
	protected void setUpView(View view) {
		newsInfo = (TextView) view.findViewById(R.id.news_fragment_info_textview);
		progressBar = (ProgressBar) view.findViewById(R.id.news_fragment_progressbar);
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.news_fragment_recyclerview);
		renderer = new NewsRenderer(recyclerView, new NewsRenderer.OnRssListener() {
			@Override
			public void onSelected(RssItem rssItem) {
				listener.onRssSelected(rssItem);
			}
		});
		setUpToolbar((Toolbar) view.findViewById(R.id.news_detail_fragment_toolbar), query);
	}
	
	void showProgress() {
		progressBar.setVisibility(View.VISIBLE);
	}
	
	void paintNews(List<RssItem> rssItems) {
		progressBar.setVisibility(View.GONE);
		if (rssItems.isEmpty()) {
			newsInfo.setText(R.string.no_news);
			newsInfo.setVisibility(View.VISIBLE);
		} else {
			renderer.paintNews(rssItems);
		}
	}
	
	void showError() {
		progressBar.setVisibility(View.GONE);
		newsInfo.setText(R.string.news_loading_error);
		newsInfo.setVisibility(View.VISIBLE);
	}
	
	private void setUpToolbar(Toolbar toolbar, String query) {
		String title = getTitleToolbar(query);
		toolbar.setTitle(title);
		toolbar.setLogo(R.mipmap.ic_launcher);
		toolbar.inflateMenu(R.menu.news_fragment_menu);
		configSearchItem(toolbar.getMenu());
		configListener(toolbar);
	}
	
	private String getTitleToolbar(String query) {
		if (!TextUtils.isEmpty(query)) {
			return query;
		} else {
			return viewContextInject(Activity.class).getString(R.string.app_name);
		}
	}
	
	private void configSearchItem(Menu menu) {
		MenuItem searchItem = menu.findItem(R.id.news_fragment_menu_searchview);
		if (searchItem != null) {
			final SearchView view = (SearchView) searchItem.getActionView();
			view.setIconifiedByDefault(true);
			view.setQueryHint(viewContextInject(Activity.class).getString(R.string.search_by_title));
			view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					view.clearFocus();
					listener.onQuery(query);
					return true;
				}
				
				@Override
				public boolean onQueryTextChange(String s) {
					return false;
				}
			});
			view.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (!hasFocus) {
						view.setIconified(true);
					}
				}
			});
		}
	}
	
	private void configListener(Toolbar toolbar) {
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.news_fragment_menu_source_setting:
						NewsView.this.setSource();
						break;
					default:
						return false;
				}
				return true;
			}
		});
	}
	
	private void setSource() {
		Activity activity = viewContextInject(Activity.class);
		final View dialogView = activity
			.getLayoutInflater()
			.inflate(R.layout.dialog_set_source, null);
		DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText editText = (EditText) dialogView.findViewById(R.id.dialog_set_source_edittext);
				NewsView.this.listener.onSourceUpdated(TextViewUtils.getText(editText));
			}
		};
		new AlertDialog.Builder(activity)
			.setTitle(R.string.setting_source)
			.setView(dialogView)
			.setPositiveButton(R.string.set_source_dialog_set, dialogListener)
			.setNegativeButton(R.string.set_source_dialog_cancel, null)
			.show();
	}
	
	interface ViewListener {
		
		void onQuery(String query);
		
		void onSourceUpdated(String newSource);
		
		void onRssSelected(RssItem rssItem);
		
	}
	
}
