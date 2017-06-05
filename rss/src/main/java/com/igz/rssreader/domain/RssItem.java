package com.igz.rssreader.domain;

public class RssItem {

	public final String imageUrl;
	public final String title;
	public final String description;
	public final String link;
	public final String publishDate;

	public RssItem(String imageUrl, String title, String description, String link, String publishDate) {
		this.imageUrl = imageUrl;
		this.title = title;
		this.description = description;
		this.link = link;
		this.publishDate = publishDate;
	}

}
