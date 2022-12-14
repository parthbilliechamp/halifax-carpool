package com.halifaxcarpool.commons.database.dao;

import com.halifaxcarpool.commons.business.beans.User;

public abstract class IUserDao {

    public abstract void registerUser(User user) throws Exception;

    public abstract boolean updateUser(User user);

}
