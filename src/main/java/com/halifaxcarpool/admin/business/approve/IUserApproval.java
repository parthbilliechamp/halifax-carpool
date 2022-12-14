package com.halifaxcarpool.admin.business.approve;

import com.halifaxcarpool.commons.business.beans.User;

import java.util.List;

public interface IUserApproval {
    List<User> getValidUserRequests();
    boolean acceptUserRequest(String id);

    boolean rejectUserRequest(String id);
}
