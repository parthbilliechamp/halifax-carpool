package com.halifaxcarpool.commons.business.authentication;

import com.halifaxcarpool.commons.business.authentication.encrypter.IPasswordEncrypter;
import com.halifaxcarpool.commons.business.authentication.encrypter.PasswordEncrypterImpl;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;

public class UserAuthenticationImpl implements IUserAuthentication {

    private static IPasswordEncrypter passwordEncrypter;
    public UserAuthenticationImpl() {
        passwordEncrypter = new PasswordEncrypterImpl();
    }
    @Override
    public User authenticateUser(String userName,
                                 String password,
                                 IUserAuthenticationDao userAuthenticationDao) {
        String encryptedPassword = null;
        try {
            encryptedPassword = passwordEncrypter.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userAuthenticationDao.authenticate(userName, encryptedPassword);
    }

}
