package com.igz.rssreader.ui.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.igz.rssreader.instrument.AppInjector;
import com.igz.rssreader.instrument.Injector;
import com.igz.rssreader.support.inject.KinjectInjector;
import com.igz.rssreader.support.inject.modules.activity.BaseActivityModule;
import com.wokdsem.kinject.core.ModuleMapper;
import com.wokdsem.kommander.Action;
import com.wokdsem.kommander.Kommand;
import com.wokdsem.kommander.KommandTokenBox;
import com.wokdsem.kommander.Kommander;

import static com.igz.rssreader.support.inject.modules.activity.BaseActivityModuleMapper.from;

public abstract class BaseActivity<V extends BaseActivityView, I extends BaseInteractor> extends AppCompatActivity {

	protected static final int TAG_INSTANCE = Integer.MAX_VALUE;
	protected static final int TAG_VIEW = Integer.MAX_VALUE - 1;
	private static final String BUNDLE_SAVE_NAVIGATOR = "SAVE_NAVIGATOR";
	protected V view;
	protected I interactor;
	protected KommandTokenBox kommandTokenBox;
	Injector appInjector;
	Injector viewContextInjector;
	private boolean isResumed;

	public BaseActivity() {
		kommandTokenBox = new KommandTokenBox();
	}

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		view = getView();
		interactor = getInteractor();
		appInjector = AppInjector.getInjector();
		viewContextInjector = new KinjectInjector(getModuleMapper());
		restoreState(savedInstanceState);
		super.onCreate(savedInstanceState);
		setContentView(view.layoutId);
		view.setUpViewContextInjector(viewContextInjector);
		view.setUpView(findViewById(android.R.id.content));
		Bundle arguments = getIntent().getExtras();
		if (arguments != null) {
			loadArguments(arguments);
		}
		onCreated(savedInstanceState);
	}

	protected abstract V getView();

	protected abstract I getInteractor();

	protected ModuleMapper getModuleMapper() {
		return from(new BaseActivityModule(this));
	}

	private void restoreState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			String navState = savedInstanceState.getString(BUNDLE_SAVE_NAVIGATOR);
			if (navState != null) {
				viewContextInject(ActivityNavigator.class).restoreInstanceState(navState);
			}
		}
	}

	protected void loadArguments(Bundle arguments) {
	}

	protected void onCreated(Bundle savedInstanceState) {
	}

	@Override
	protected void onResume() {
		super.onResume();
		isResumed = true;
	}

	@Override
	@CallSuper
	protected void onPause() {
		super.onPause();
		isResumed = false;
		kommandTokenBox.cancel(TAG_VIEW);
	}

	@CallSuper
	@Override
	protected void onDestroy() {
		super.onDestroy();
		kommandTokenBox.cancelAll();
	}

	@Override
	public final void onBackPressed() {
		if (!view.onBackIntercept()) {
			ActivityNavigator activityNavigator = viewContextInject(ActivityNavigator.class);
			Fragment activeFragment = activityNavigator.getActiveFragment();
			if (activeFragment != null && activeFragment instanceof BaseFragment) {
				BaseFragment fragment = (BaseFragment) activeFragment;
				if (fragment.onBackPressed()) {
					return;
				}
			}
			if (!(onBackIntercept() || activityNavigator.navigateBack() || onFinishTransitionIntercept())) {
				supportFinishAfterTransition();
			}
		}
	}

	protected boolean onBackIntercept() {
		return false;
	}

	protected boolean onFinishTransitionIntercept() {
		return false;
	}

	protected void setUp(ViewNavigator.NavConf navConf) {
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		String saveInstanceState = viewContextInject(ActivityNavigator.class).saveInstanceState();
		outState.putString(BUNDLE_SAVE_NAVIGATOR, saveInstanceState);
	}

	public boolean isBaseResumed() {
		return isResumed;
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

	final int getFrameContainer() {
		return view.containerId;
	}

}
