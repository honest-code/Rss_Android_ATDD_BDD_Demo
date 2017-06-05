package com.igz.rssreader.support;

import android.support.test.InstrumentationRegistry;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MockResponseBodyProvider {

	public static String loadFile(String assetPath) {
		try {
			InputStream open = InstrumentationRegistry.getContext()
				.getResources()
				.getAssets()
				.open(assetPath);
			return readFile(open);
		} catch (IOException e) {
			return "";
		}
	}

	private static String readFile(InputStream is) {
		Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
		return scanner.hasNext() ? scanner.next() : "";
	}

}
