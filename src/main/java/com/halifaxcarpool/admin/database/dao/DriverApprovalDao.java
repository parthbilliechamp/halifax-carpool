package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.util.List;

public interface DriverApprovalDao {
    List<User> getPendingApprovalDrivers();

    boolean acceptDriverRequest(String id);

    boolean rejectDriverRequest(String id);
}
