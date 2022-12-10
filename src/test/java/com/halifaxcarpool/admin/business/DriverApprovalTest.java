package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.business.approve.DriverApproval;
import com.halifaxcarpool.admin.business.approve.UserApproval;
import com.halifaxcarpool.admin.database.dao.DriverApprovalDao;
import com.halifaxcarpool.admin.database.dao.DriverApprovalDaoMock;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.driver.business.beans.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
public class DriverApprovalTest{

    private DriverApprovalDao driverApprovalDao = new DriverApprovalDaoMock();
    private UserApproval userApproval = new DriverApproval(driverApprovalDao);

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
