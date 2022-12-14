package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.commons.business.beans.User;

import java.util.List;

public interface IDriverApprovalDao {
    List<User> getPendingApprovalDrivers();

    boolean acceptDriverRequest(String id);

    boolean rejectDriverRequest(String id);
}
