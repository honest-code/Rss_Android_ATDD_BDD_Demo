package com.igz.rssreader.instrument;

public interface Injector {

	<T> T inject(Class<T> tClass);

}
