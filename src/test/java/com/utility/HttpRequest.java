package com.utility;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.Logger;

public class HttpRequest {

	private static final Logger log = LoggerHelper.getLogger(HttpRequest.class);

	public static int getStatusCode(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			return connection.getResponseCode();

		} catch (Exception e) {
			log.warn("Error: " + e.getMessage());
			return -1;
		}
	}

}