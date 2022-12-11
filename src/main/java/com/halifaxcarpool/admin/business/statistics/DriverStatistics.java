package com.halifaxcarpool.admin.business.statistics;

import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.driver.database.dao.DriverDetailsDaoImpl;

public class DriverStatistics extends IUserStatisticsBuilder {

    public DriverStatistics(IUserDetails driverDetails){
        super();
        userDetails = driverDetails;
    }
}
