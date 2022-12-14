package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.customer.business.beans.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerAuthenticationDaoMockImpl implements IUserAuthenticationDao {

    private static final Map<List<String>, Customer> mockCustomerData = new HashMap<>();
    private static final List<String> emailAndPasswordList = new ArrayList<>();

    static {
        populateMockCustomerData();
    }

    private static void populateMockCustomerData() {

        emailAndPasswordList.clear();
        emailAndPasswordList.add("johnwick@shool.ca");
        emailAndPasswordList.add("1f418e87b935ad8b9eda29663c2557a9");
        mockCustomerData.put(emailAndPasswordList, new Customer(1, "John", "5321123621", "johnwick@shool.ca", "johncena1234"));

        emailAndPasswordList.clear();
        emailAndPasswordList.add("klklkl@gmail.ca");
        emailAndPasswordList.add("316842c33a0beab5506ce2ba4b97a29e");
        mockCustomerData.put(emailAndPasswordList, new Customer(4, "Kane Lart", "9532120333", "klklkl@gmail.ca", "kllk9009@3"));

        emailAndPasswordList.clear();
        emailAndPasswordList.add("h.klols@hotmail.ca");
        emailAndPasswordList.add("b8fec51678e29c2c1338f0630477c422");
        mockCustomerData.put(emailAndPasswordList, new Customer(3, "Kelly H", "9855632147", "h.klols@hotmail.ca", "sweatKhotH@@4"));

        emailAndPasswordList.clear();
        emailAndPasswordList.add("hc93334@dal.ca");
        emailAndPasswordList.add("ccb0e9acb24ed18c90e3115a19e40203");
        mockCustomerData.put(emailAndPasswordList, new Customer(10, "Henna Cook", "7851652744", "hc93334@dal.ca", "HC8282404?"));
    }

    @Override
    public Customer authenticate(String username, String password) {

        emailAndPasswordList.clear();
        emailAndPasswordList.add(username);
        emailAndPasswordList.add(password);

        return mockCustomerData.get(emailAndPasswordList);
    }

}
