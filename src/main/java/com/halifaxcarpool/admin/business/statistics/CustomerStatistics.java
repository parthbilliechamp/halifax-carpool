package com.halifaxcarpool.admin.business.statistics;

import com.halifaxcarpool.admin.database.dao.IUserDetails;

public class CustomerStatistics extends IUserStatisticsBuilder {
    public CustomerStatistics(IUserDetails customerDetails){
        userDetails = customerDetails;
    }
}
