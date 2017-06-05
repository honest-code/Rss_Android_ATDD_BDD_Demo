package com.igz.rssreader.support.ui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class NotParcelled {

	private static final Gson gson = new Gson();

	public static <T> String toNotParcelled(T t) {
		return gson.toJson(t);
	}

	public static <T> T fromNotParcelled(String notParcelled, Class<T> tClass) {
		try {
			return gson.fromJson(notParcelled, tClass);
		} catch (JsonSyntaxException e) {
			return null;
		}
	}

}
