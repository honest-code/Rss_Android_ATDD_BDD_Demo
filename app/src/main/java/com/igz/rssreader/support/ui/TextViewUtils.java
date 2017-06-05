package com.igz.rssreader.support.ui;

import android.widget.TextView;

public class TextViewUtils {

	public static String getText(TextView textView) {
		CharSequence text = textView.getText();
		return text.toString();
	}

}
