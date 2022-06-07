package com.sbmtech.common.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class OTPGenerator {
	private static byte[] getSecureRandomSeed() {

		SecureRandom randomSeed = new SecureRandom();
		String keyText = "rKeASwX2i8eFTwQr";

		// Display the algorithm name
		byte[] secureSeed = null;
		try {

			SecretKeySpec newKey = new SecretKeySpec(keyText.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, newKey);
			secureSeed = cipher.doFinal(String.valueOf(randomSeed.nextLong()).getBytes("UTF-8"));

		} catch (UnsupportedEncodingException e) {

		} catch (IllegalBlockSizeException e) {

		} catch (NoSuchPaddingException e) {

		} catch (NoSuchAlgorithmException e) {

		} catch (BadPaddingException e) {

		} catch (Exception e) {

		}

		return secureSeed;
	}

	public static Long getCode() {

		SecureRandom r = new SecureRandom();

		r.setSeed(getSecureRandomSeed());

		return (long) ((1 + r.nextInt(9)) * 10000 + r.nextInt(10000));
	}

	public static int get4DigitsCode() {

		SecureRandom r = new SecureRandom();

		r.setSeed(getSecureRandomSeed());

		return ((1 + r.nextInt(9)) * 1000 + r.nextInt(1000));
	}

}
