package com.halifaxcarpool.admin.business.approve;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.util.List;

public interface UserApproval {
    List<User> getValidUserRequests();
    boolean acceptUserRequest(String id);

    boolean rejectUserRequest(String id);
}
