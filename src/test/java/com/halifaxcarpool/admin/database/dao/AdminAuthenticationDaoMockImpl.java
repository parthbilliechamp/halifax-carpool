package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.customer.business.beans.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAuthenticationDaoMockImpl implements IAdminAuthenticationDao{

    private static final Map<List<String>, Admin> mockAdminData = new HashMap<>();
    private static final List<String> emailAndPasswordList = new ArrayList<>();

    static {
        populateMockAdminData();
    }

    private static void populateMockAdminData() {

        emailAndPasswordList.clear();
        emailAndPasswordList.add("admin");
        emailAndPasswordList.add("21232f297a57a5a743894a0e4a801fc3");
        mockAdminData.put(emailAndPasswordList, new Admin(1, "admin", "admin"));

    }

    @Override
    public Admin authenticate(String username, String password) {

        emailAndPasswordList.clear();
        emailAndPasswordList.add(username);
        emailAndPasswordList.add(password);

        return mockAdminData.get(emailAndPasswordList);
    }
}
