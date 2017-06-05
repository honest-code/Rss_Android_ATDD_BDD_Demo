package com.igz.rssreader.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.igz.rssreader.instrument.Injector;
import com.wokdsem.kommander.Action;
import com.wokdsem.kommander.Kommand;
import com.wokdsem.kommander.KommandTokenBox;
import com.wokdsem.kommander.Kommander;

public abstract class BaseFragment<V extends BaseFragmentView, I extends BaseInteractor> extends Fragment {

	protected static final int TAG_INSTANCE = Integer.MAX_VALUE;
	protected static final int TAG_VIEW = Integer.MAX_VALUE - 1;

	protected V fragmentView;
	protected I interactor;
	protected KommandTokenBox kommandTokenBox;
	private Injector appInjector;
	private Injector viewContextInjector;

	public BaseFragment() {
		kommandTokenBox = new KommandTokenBox();
	}

	@Override
	public final void onAttach(Context context) {
		super.onAttach(context);
		appInjector = ((BaseActivity) context).appInjector;
		viewContextInjector = ((BaseActivity) context).viewContextInjector;
	}

	@Override
	@CallSuper
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle arguments = getArguments();
		if (arguments != null) {
			loadArguments(arguments);
		}
		interactor = getInteractor();
	}

	protected void loadArguments(Bundle arguments) {
	}

	protected abstract I getInteractor();

	@Nullable
	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fragmentView = getFragmentView();
		fragmentView.setUp(viewContextInjector);
		fragmentView.setUpFragmentView(inflater, container);
		return fragmentView.view;
	}

	protected abstract V getFragmentView();

	@Override
	public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		onViewCreated();
	}

	protected void onViewCreated() {
	}

	@Override
	@CallSuper
	public void onDestroyView() {
		super.onDestroyView();
		fragmentView = null;
		if (!getActivity().isChangingConfigurations() || !getRetainInstance()) {
			kommandTokenBox.cancel(TAG_VIEW);
		}
	}

	@CallSuper
	@Override
	public void onDestroy() {
		super.onDestroy();
		kommandTokenBox.cancelAll();
	}

	final boolean onBackPressed() {
		return fragmentView.onBackIntercept() || onBackIntercept();
	}

	protected boolean onBackIntercept() {
		return false;
	}

	protected final <T> T appInject(Class<T> tClass) {
		return appInjector.inject(tClass);
	}

	protected final <T> T viewContextInject(Class<T> tClass) {
		return viewContextInjector.inject(tClass);
	}

	protected final <T> Kommand<T> makeKommand(Action<T> action) {
		return appInject(Kommander.class).makeKommand(action);
	}

}
