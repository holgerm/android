package com.qeevee.gq.util.test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class NetTest {

	private static final String TEST_SERVERNAME = "www.google.com";
	private static final String TEST_URL_STRING = "http://www.google.com";

	public static boolean isDNSAvailable() {
		return isDNSAvailable(TEST_SERVERNAME);
	}

	private static boolean isDNSAvailable(String serverName) {
		try {
			InetAddress address = InetAddress.getByName(serverName);
			return address != null;
		} catch (UnknownHostException uhExc) {
			return false;
		}
	}

	public static boolean weAreOnline() {
		return isServerReachable(TEST_URL_STRING);
	}

	public static boolean isServerReachable(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.getContent(); // we do not do anything with the content
										// but provoke an exception is we are
										// offline.
			return true;
		} catch (IOException ioExc) {
			return false;
		}
	}
}
