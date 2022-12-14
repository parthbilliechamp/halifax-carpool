package com.halifaxcarpool.commons.business.authentication.encrypter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypterImpl implements IPasswordEncrypter {

    private static final String ALGORITHM = "MD5";
    private static final int RADIX = 16;

    private static final int BEGIN_INDEX = 1;
    @Override
    public String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
        messageDigest.update(text.getBytes());
        byte[] bytes = messageDigest.digest();
        StringBuilder builder = new StringBuilder();
        for (byte aByte : bytes) {
            int index = (aByte & 0xff) + 0x100;
            String byteToString =
                    Integer.toString(index, RADIX).substring(BEGIN_INDEX);
            builder.append(byteToString);
        }
        return builder.toString();
    }

}
