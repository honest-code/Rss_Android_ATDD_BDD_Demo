package com.igz.rssreader.support.inject.modules.app;

import android.content.Context;
import com.android.intelygenz.kvksp.SpKeeper;
import com.igz.rssreader.BuildConfig;
import com.intelygenz.android.KeyValueKeeper;
import com.intelygenz.android.netclient.NetClient;
import com.intelygenz.android.netokclient.NetOkClient;
import com.wokdsem.kinject.annotations.Module;
import com.wokdsem.kinject.annotations.Provides;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

@Module
public class DepModule {

	@Provides
	public NetClient provideNetClient(Context context) {
		return new NetOkClient.Builder(context).logLevel(BuildConfig.DEBUG ? BODY : NONE).build();
	}

	@Provides
	public KeyValueKeeper provideKvKeeper(Context context) {
		return new SpKeeper(context);
	}

}
