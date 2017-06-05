package com.igz.rssreader.ui.feats.home.newsdetail;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.igz.rssreader.R;
import com.igz.rssreader.domain.RssItem;
import com.igz.rssreader.ui.base.BaseFragmentView;
import com.squareup.picasso.Picasso;

class NewsDetailView extends BaseFragmentView {

	private final String title;
	private final OnNewsDetailListener listener;
	private ImageView image;
	private TextView description;
	private View browserButton;

	NewsDetailView(String title, OnNewsDetailListener listener) {
		super(R.layout.news_detail_fragment);
		this.listener = listener;
		this.title = title;
	}

	@Override
	protected void setUpView(View view) {
		image = (ImageView) view.findViewById(R.id.news_detail_fragment_detail_image_imageview);
		description = ((TextView) view.findViewById(R.id.news_detail_fragment_full_description_textview));
		browserButton = view.findViewById(R.id.news_detail_fragment_go_browser_button);
		setUpToolbar((Toolbar) view.findViewById(R.id.news_detail_fragment_toolbar), title);
	}

	void paintRss(final RssItem item) {
		Picasso.with(viewContextInject(Activity.class))
			.load(item.imageUrl)
			.placeholder(R.mipmap.ic_launcher)
			.fit()
			.centerCrop()
			.into(image);
		description.setText(item.description);
		browserButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onShowInBrowser(item.link);
			}
		});
	}

	private void setUpToolbar(Toolbar toolbar, String title) {
		toolbar.setTitle(title);
		toolbar.setNavigationIcon(R.drawable.ic_back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onBack();
			}
		});
	}

	interface OnNewsDetailListener {

		void onBack();

		void onShowInBrowser(String link);
	}

}
