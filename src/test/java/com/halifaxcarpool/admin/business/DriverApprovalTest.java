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

@SpringBootTest
@ActiveProfiles("test")
public class DriverApprovalTest{

    private DriverApprovalDao driverApprovalDao = new DriverApprovalDaoMock();
    private UserApproval userApproval = new DriverApproval(driverApprovalDao);

    @Test
    public void getValidUserRequestsTest(){
        List<User> filteredDrivers = userApproval.getValidUserRequests();
        assert filteredDrivers.size() == 1;
    }

}
