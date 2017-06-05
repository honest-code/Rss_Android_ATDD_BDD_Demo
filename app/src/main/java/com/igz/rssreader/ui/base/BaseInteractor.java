package com.igz.rssreader.ui.base;

import com.igz.rssreader.instrument.AppInjector;
import com.igz.rssreader.instrument.Injector;
import com.wokdsem.kommander.Kommander;

public abstract class BaseInteractor {

	public static final BaseInteractor EMPTY_INTERACTOR;

	static {
		EMPTY_INTERACTOR = new BaseInteractor() {
		};
	}

	protected final Kommander kommander;
	private final Injector injector;

	public BaseInteractor() {
		injector = AppInjector.getInjector();
		kommander = injector.inject(Kommander.class);
	}

	protected final <T> T inject(Class<T> tClass) {
		return injector.inject(tClass);
	}

}
