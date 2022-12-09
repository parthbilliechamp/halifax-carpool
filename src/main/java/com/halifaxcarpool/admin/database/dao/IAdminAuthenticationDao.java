package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.admin.business.beans.Admin;

public interface IAdminAuthenticationDao {

    Admin authenticate(String username, String password);

}
