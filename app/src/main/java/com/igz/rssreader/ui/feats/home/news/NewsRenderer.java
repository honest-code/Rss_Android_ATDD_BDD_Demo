package com.igz.rssreader.ui.feats.home.news;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.igz.rssreader.R;
import com.igz.rssreader.domain.RssItem;
import com.squareup.picasso.Picasso;
import java.util.List;

class NewsRenderer {

	private final RecyclerView newsView;
	private final OnRssListener listener;

	NewsRenderer(RecyclerView newsView, OnRssListener listener) {
		this.newsView = newsView;
		this.listener = listener;
		newsView.setLayoutManager(new LinearLayoutManager(newsView.getContext(), LinearLayoutManager.VERTICAL, false));
		newsView.setHasFixedSize(true);
	}

	void paintNews(List<RssItem> items) {
		newsView.setAdapter(new NewsAdapter(items));
	}

	interface OnRssListener {

		void onSelected(RssItem selected);

	}

	private static class NewsHolder extends RecyclerView.ViewHolder {

		private RssItem rssRssItem;
		private ImageView image;
		private TextView title, description;

		NewsHolder(ViewGroup viewGroup, final OnRssListener listener) {
			super(getView(viewGroup));
			image = (ImageView) itemView.findViewById(R.id.news_holder_component_imageview);
			title = (TextView) itemView.findViewById(R.id.news_holder_component_title_textview);
			description = (TextView) itemView.findViewById(R.id.news_holder_component_description_textview);
			View cardView = itemView.findViewById(R.id.news_holder_component_cardview);
			cardView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onSelected(rssRssItem);
				}
			});
		}

		private static View getView(ViewGroup viewGroup) {
			return LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.news_holder_component, viewGroup, false);
		}

		private void paintNew(RssItem rssRssItem) {
			this.rssRssItem = rssRssItem;
			Picasso.with(itemView.getContext())
				.load(rssRssItem.imageUrl)
				.placeholder(R.mipmap.ic_launcher)
				.fit()
				.into(image);
			title.setText(rssRssItem.title);
			description.setText(rssRssItem.description);
		}

	}

	private class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

		private final List<RssItem> items;

		private NewsAdapter(List<RssItem> items) {
			this.items = items;
		}

		@Override
		public NewsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
			return new NewsHolder(viewGroup, listener);
		}

		@Override
		public void onBindViewHolder(NewsHolder newsHolder, int position) {
			RssItem currentNew = items.get(position);
			newsHolder.paintNew(currentNew);
		}

		@Override
		public int getItemCount() {
			return items.size();
		}

	}

}
