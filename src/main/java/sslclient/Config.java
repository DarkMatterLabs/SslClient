package sslclient;

import java.io.File;
import java.net.InetSocketAddress;

public class Config {
	public static File getFileProperty(String key) {
		if (!System.getProperties().containsKey(key)) {
			throw new RuntimeException("Missing required system property -D" + key);
		}
		String name = System.getProperty(key);
		File file = new File(name);
		if (!file.isFile()) {
			throw new RuntimeException("Cannot read file for property -D" + key + "=" + file.getAbsolutePath());
		}
		return file;
	}

	public static InetSocketAddress getAddressProperty(String key) {
		if (!System.getProperties().containsKey(key)) {
			throw new RuntimeException("Missing required system property -D" + key);
		}
		String value = System.getProperty(key);
		String[] parts = value.split(":");
		if (parts.length != 2) {
			throw new RuntimeException("Invalid format for -D" + key + "=" + value + ", expected address:port");
		}
		String address = parts[0];
		String portStr = parts[1];
		int port;
		try {
			port = Integer.parseInt(portStr);
		} catch (NumberFormatException ex) {
			throw new RuntimeException("Invalid format for -D" + key + "=" + value + ", invalid port number " + portStr);
		}

		return new InetSocketAddress(address, port);
	}
}
