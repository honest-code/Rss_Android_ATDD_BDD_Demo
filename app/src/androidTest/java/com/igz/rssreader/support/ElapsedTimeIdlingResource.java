package com.igz.rssreader.support;

import android.support.test.espresso.IdlingResource;

@Deprecated
public class ElapsedTimeIdlingResource implements IdlingResource {

	private final long startTime;
	private final long waitingTime;
	private ResourceCallback resourceCallback;

	public ElapsedTimeIdlingResource(long waitingTime) {
		this.startTime = System.currentTimeMillis();
		this.waitingTime = waitingTime;
	}

	@Override
	public String getName() {
		return ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
	}

	@Override
	public boolean isIdleNow() {
		long elapsed = System.currentTimeMillis() - startTime;
		boolean idle = (elapsed >= waitingTime);
		System.out.println("IDLE | elapsed: " + elapsed + " | starttime: " + startTime + " | waiting: " + waitingTime);
		if (idle) {
			resourceCallback.onTransitionToIdle();
		}
		return idle;
	}

	@Override
	public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
		this.resourceCallback = resourceCallback;
	}

}