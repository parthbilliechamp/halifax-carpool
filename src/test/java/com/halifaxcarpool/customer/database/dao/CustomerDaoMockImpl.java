package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerDaoMockImpl implements ICustomerDao {

    private static Map<Integer, Customer> mockCustomerData = new HashMap<>();

    static {
        populateMockCustomerData();
    }

    private static void populateMockCustomerData() {

        int customerId = 1;
        mockCustomerData.put(customerId, new Customer(customerId, "John", "5321123621", "johnwick@shool.ca", "johncena1234"));

        customerId = 4;
        mockCustomerData.put(customerId, new Customer(customerId, "Kane Lart", "9532120333", "klklkl@gmail.ca", "kllk9009@3"));

        customerId = 3;
        mockCustomerData.put(customerId, new Customer(customerId, "Kelly H", "9855632147", "h.klols@hotmail.ca", "sweatKhotH@@4"));

        customerId = 10;
        mockCustomerData.put(customerId, new Customer(customerId, "Henna Cook", "7851652744", "hc93334@dal.ca", "HC8282404?"));
    }

    @Override
    public void registerCustomer(Customer customer) throws Exception {
        int customerId;
        customerId = customer.getCustomerId();
        if(mockCustomerData.containsKey(customerId)) {
            throw new Exception("Customer Already Exists");
        }
        mockCustomerData.put(customerId, customer);
    }

    @Override
    public void updateCustomerProfile(Customer customer) {

        int customerId;
        customerId = customer.getCustomerId();

        mockCustomerData.get(customerId);

        mockCustomerData.get(customerId).setCustomerId(customer.getCustomerId());
        mockCustomerData.get(customerId).setCustomerName(customer.getCustomerName());
        mockCustomerData.get(customerId).setCustomerContact(customer.getCustomerContact());
        mockCustomerData.get(customerId).setCustomerEmail(customer.getCustomerEmail());
        mockCustomerData.get(customerId).setCustomerPassword(customer.getCustomerPassword());

    }

}

