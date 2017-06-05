package com.igz.rssreader.ui.base;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableList;

public final class ActivityNavigator {
	
	public static final NavSettings DF_SETTINGS;
	public static final NavSettings B_REF_SETTINGS;

	private static final String TAG_BACK = "B";
	private static final String TAG_UP_REFERENCE = "R";
	
	static {
		DF_SETTINGS = new NavSettingsBuilder().isAnimated(true)
			.build();
		B_REF_SETTINGS = new NavSettingsBuilder().clearStack(true)
			.isReference(true)
			.build();
	}
	
	private final FragmentManager fragmentManager;
	private final List<Set<String>> tagIds;
	private final int container;

	public ActivityNavigator(BaseActivity activity) {
		fragmentManager = activity.getSupportFragmentManager();
		this.container = activity.getFrameContainer();
		this.tagIds = new ArrayList<>();
	}
	
	public void navigate(Fragment fragment) {
		navigate(fragment, DF_SETTINGS);
	}
	
	public void navigate(Fragment fragment, NavSettings settings) {
		clearStack(settings.clearStack);
		Set<String> transitionTagIds = getFragmentTagIds(settings.isReference, settings.tagIds);
		tagIds.add(transitionTagIds);
		String fragmentTag = getNameFromIndex(tagIds.size());
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if (settings.isAnimated) {
			transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in,
											android.R.anim.fade_out);
		}
		transaction.addToBackStack(fragmentTag)
			.replace(container, fragment, fragmentTag)
			.commit();
	}

	private void clearStack(boolean clearStack) {
		if (clearStack) {
			fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			tagIds.clear();
		}
	}

	private Set<String> getFragmentTagIds(boolean isReference, List<Integer> tags) {
		HashSet<String> tagIds = new HashSet<>();
		tagIds.add(TAG_BACK);
		if (isReference) {
			tagIds.add(TAG_UP_REFERENCE);
		}
		for (int tagId : tags) {
			tagIds.add(String.valueOf(tagId));
		}
		return tagIds;
	}
	
	public boolean navigateBack() {
		return navigateUpTo(TAG_BACK);
	}
	
	public boolean navigateUp() {
		return navigateUpTo(TAG_UP_REFERENCE);
	}

	public boolean navigateUpTo(int tagId) {
		String fTagId = String.valueOf(tagId);
		return navigateUpTo(fTagId);
	}
	
	private boolean navigateUpTo(String tagId) {
		for (int index = tagIds.size() - 2; index > -1; index--) {
			Set<String> fTagIds = tagIds.get(index);
			if (fTagIds.contains(tagId)) {
				int itemsToRemove = tagIds.size() - (index + 1);
				for (int nTimes = 0; nTimes < itemsToRemove; nTimes++) {
					tagIds.remove(tagIds.size() - 1);
				}
				String name = getNameFromIndex(index + 1);
				fragmentManager.popBackStack(name, 0);
				return true;
			}
		}
		return false;
	}
	
	@Nullable
	public Fragment getActiveFragment() {
		String tag = getNameFromIndex(getFragmentCount());
		return fragmentManager.findFragmentByTag(tag);
	}
	
	int getFragmentCount() {
		return tagIds.size();
	}

	private String getNameFromIndex(int index) {
		return String.valueOf("AN_" + index);
	}
	
	String saveInstanceState() {
		StringBuilder stateBuilder = new StringBuilder();
		for (Set<String> tagId : tagIds) {
			for (String tag : tagId) {
				stateBuilder.append(tag);
				stateBuilder.append("_");
			}
			stateBuilder.append('&');
		}
		return stateBuilder.toString();
	}
	
	void restoreInstanceState(String savedInstance) {
		for (String serializedTagId : savedInstance.split("&")) {
			HashSet<String> tagId = new HashSet<>();
			Collections.addAll(tagId, serializedTagId.split("_"));
			tagIds.add(tagId);
		}
	}
	
	public static class NavSettings {
		
		final boolean clearStack;
		final boolean isReference;
		final boolean isAnimated;
		final List<Integer> tagIds;
		
		private NavSettings(NavSettingsBuilder builder) {
			this.clearStack = builder.clearStack;
			this.isReference = builder.isReference;
			this.isAnimated = builder.isAnimated;
			this.tagIds = unmodifiableList(builder.tagIds);
		}
		
		public NavSettingsBuilder toBuild() {
			NavSettingsBuilder navSettingsBuilder = new NavSettingsBuilder();
			for (Integer tagId : tagIds) {
				navSettingsBuilder.addTagId(tagId);
			}
			return navSettingsBuilder.clearStack(clearStack)
				.isReference(isReference)
				.isAnimated(isAnimated);
		}
		
	}
	
	public static class NavSettingsBuilder {
		
		private boolean clearStack;
		private boolean isReference;
		private boolean isAnimated;
		private List<Integer> tagIds;
		
		public NavSettingsBuilder() {
			tagIds = new LinkedList<>();
		}
		
		public NavSettingsBuilder clearStack(boolean clearStack) {
			this.clearStack = clearStack;
			return this;
		}
		
		public NavSettingsBuilder isReference(boolean isReference) {
			this.isReference = isReference;
			return this;
		}
		
		public NavSettingsBuilder isAnimated(boolean animated) {
			isAnimated = animated;
			return this;
		}
		
		public NavSettingsBuilder addTagId(int tagId) {
			tagIds.add(tagId);
			return this;
		}
		
		public NavSettings build() {
			return new NavSettings(this);
		}
		
	}
	
}
