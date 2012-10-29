package com.qeevee.ui;

import android.webkit.WebView;

public class WebViewUtil {

	public static void showTextInWebView(WebView view, String string) {
		String prefix = "<body style=\"text-align:justify;color:#FFFFFF\">";
		String postfix = "</body>";
		view.loadDataWithBaseURL("", prefix + string + postfix, "text/html", "UTF-8", "");
		view.setBackgroundColor(0x00000000);
	}

}
