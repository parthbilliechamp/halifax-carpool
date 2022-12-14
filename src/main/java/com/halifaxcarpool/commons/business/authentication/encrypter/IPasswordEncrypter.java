package com.halifaxcarpool.commons.business.authentication.encrypter;

import java.security.NoSuchAlgorithmException;

public interface IPasswordEncrypter {

    String encrypt(String text) throws NoSuchAlgorithmException;
}
