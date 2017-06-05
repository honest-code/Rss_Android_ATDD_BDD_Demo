package com.igz.rssreader.ui.base;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.igz.rssreader.instrument.Injector;

public class BaseFragmentView {

	private final int layoutId;
	View view;
	private Injector viewContextInjector;

	public BaseFragmentView(@LayoutRes int layoutId) {
		this.layoutId = layoutId;
	}

	void setUp(Injector viewContextInjector) {
		this.viewContextInjector = viewContextInjector;
	}

	void setUpFragmentView(LayoutInflater inflater, ViewGroup container) {
		this.view = inflater.inflate(layoutId, container, false);
		setUpView(view);
	}

	protected void setUpView(View view) {
	}

	protected boolean onBackIntercept() {
		return false;
	}

	protected <T> T viewContextInject(Class<T> tClass) {
		return viewContextInjector.inject(tClass);
	}

}
