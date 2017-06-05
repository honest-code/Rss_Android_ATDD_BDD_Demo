package com.igz.rssreader.support.inject;

import com.igz.rssreader.instrument.Injector;
import com.wokdsem.kinject.Kinject;
import com.wokdsem.kinject.core.ModuleMapper;

public class KinjectInjector implements Injector {

	private Kinject injector;

	public KinjectInjector(ModuleMapper mapper) {
		injector = Kinject.instantiate(mapper);
	}

	@Override
	public <T> T inject(Class<T> tClass) {
		return injector.get(tClass);
	}

}
