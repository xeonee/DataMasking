package com.dataMasking;
public class CaesarCipher {

	public static String encryptMessage(String msg) {
		int key_letter = 4;
		int key_digit = 4;

		if(msg == null || msg == "")
			return null;
		msg = msg.toUpperCase();
		String result = "";
		for (int i = 0; i < msg.length(); i++)
			result += encryptChar(msg.charAt(i), key_letter, key_digit);
		return result;
	}

	private static char encryptChar(char c, int key_letter, int key_digit) {
		if (Character.isLetter(c))
			return (char) ('A' + (c - 'A' + key_letter) % 26);
		else if (Character.isDigit(c)){
			return (char) ('0' + (c - '0' + key_digit) % 10);
		}
		else
			return c;
	}
}
