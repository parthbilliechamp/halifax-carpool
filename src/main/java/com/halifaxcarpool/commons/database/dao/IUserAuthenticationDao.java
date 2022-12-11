package com.halifaxcarpool.commons.database.dao;

import com.halifaxcarpool.commons.business.beans.User;

public interface IUserAuthenticationDao {

    User authenticate(String username, String password);

}
