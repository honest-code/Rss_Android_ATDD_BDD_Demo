package com.igz.rssreader.instrument;

public class AppInjector {

	private static Injector injector;

	private AppInjector() {
	}

	public static void initInjector(Injector injector) {
		AppInjector.injector = injector;
	}

	public static Injector getInjector() {
		if (injector == null) {
			throw new AssertionError("Uninitialized injector error!");
		}
		return injector;
	}

	public static <T> T inject(Class<T> tClass) {
		return getInjector().inject(tClass);
	}

}
