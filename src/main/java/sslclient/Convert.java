package sslclient;

import java.io.ByteArrayOutputStream;

public class Convert {

	public static String toHex(byte[] buf, int len) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<len; i++) {
			sb.append(Character.forDigit((buf[i] >> 4) & 0xF, 16));
			sb.append(Character.forDigit((buf[i] & 0xF), 16));
		}
		return sb.toString();
	}

	public static byte[] fromHex(String hex) {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException("Invalid hex input, odd number of digits");
		}
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for(int i = 0; i < hex.length(); i += 2) {
			String digits = hex.substring(i, i + 2);
			try {
            	out.write(Integer.parseInt(digits, 16));
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Invalid hex input: " + digits);
			}
        }
        return out.toByteArray();
    }
}
