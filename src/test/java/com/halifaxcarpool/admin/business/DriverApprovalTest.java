package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.business.approve.DriverApproval;
import com.halifaxcarpool.admin.business.approve.IUserApproval;
import com.halifaxcarpool.admin.database.dao.IDriverApprovalDao;
import com.halifaxcarpool.admin.database.dao.DriverApprovalDaoMock;
import com.halifaxcarpool.commons.business.beans.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
public class DriverApprovalTest{

    private IAdminModelFactory adminModelFactory = new AdminModelFactory();
    private IAdminDaoFactory adminDaoFactory = new AdminDaoTestFactory();

    private IDriverApprovalDao driverApprovalDao = adminDaoFactory.getDriverApprovalDao();
    private IUserApproval userApproval = adminModelFactory.getDriverApproval(driverApprovalDao);

    private static final String LICENSE_EXIST = "1";
    private static final String LICENSE_NOT_EXIST = "4";

    @Test
    public void getValidUserRequestsTest(){
        List<User> filteredDrivers = userApproval.getValidUserRequests();
        assert filteredDrivers.size() == 1;
    }

    @Test
    public void acceptUserRequestTest(){
        assert userApproval.acceptUserRequest(LICENSE_EXIST);
    }

    @Test
    public void acceptUserRequestNotFoundTest(){
        assertFalse(userApproval.acceptUserRequest(LICENSE_NOT_EXIST));
    }

    @Test
    public void rejectUserRequestTest(){
        assert userApproval.rejectUserRequest(LICENSE_EXIST);
    }

    @Test
    public void rejectUserRequestNotFoundTest(){
        assertFalse(userApproval.rejectUserRequest(LICENSE_NOT_EXIST));
    }

}
