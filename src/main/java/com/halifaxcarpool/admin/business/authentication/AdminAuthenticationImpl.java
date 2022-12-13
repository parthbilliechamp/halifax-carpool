package com.halifaxcarpool.admin.business.authentication;

import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.admin.database.dao.IAdminAuthenticationDao;
import com.halifaxcarpool.commons.business.authentication.encrypter.IPasswordEncrypter;
import com.halifaxcarpool.commons.business.authentication.encrypter.PasswordEncrypterImpl;

public class AdminAuthenticationImpl implements IAdminAuthentication {

    private static IPasswordEncrypter passwordEncrypter;

    public AdminAuthenticationImpl() {
        passwordEncrypter = new PasswordEncrypterImpl();
    }

    @Override
    public Admin authenticate(String userName,
                              String password,
                              IAdminAuthenticationDao adminAuthenticationDao) {

        String encryptedPassword = null;
        try {
            encryptedPassword = passwordEncrypter.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminAuthenticationDao.authenticate(userName, encryptedPassword);
    }

}
