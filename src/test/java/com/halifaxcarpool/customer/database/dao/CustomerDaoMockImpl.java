package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.business.beans.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerDaoMockImpl extends IUserDao {

    private static final Map<Integer, Customer> mockCustomerData = new HashMap<>();

    static {
        populateMockCustomerData();
    }

    private static void populateMockCustomerData() {
        int customer_id = 1;
        mockCustomerData.put(customer_id, new Customer(1, "John", "5321123621", "johnwick@shool.ca", "johncena1234"));

        customer_id = 4;
        mockCustomerData.put(customer_id, new Customer(4, "Kane Lart", "9532120333", "klklkl@gmail.ca", "kllk9009@3"));

        customer_id = 3;
        mockCustomerData.put(customer_id, new Customer(3, "Kelly H", "9855632147", "h.klols@hotmail.ca", "sweatKhotH@@4"));

        customer_id = 10;
        mockCustomerData.put(customer_id, new Customer(10, "Henna Cook", "7851652744", "hc93334@dal.ca", "HC8282404?"));
    }

    @Override
    public void registerUser(User user) throws Exception {
        Customer customer = (Customer) user;
        int customerId;
        customerId = customer.getCustomerId();
        if(mockCustomerData.containsKey(customerId)) {
            throw new Exception("Customer Already Exists");
        }
        mockCustomerData.put(customerId, customer);
    }

    @Override
    public boolean updateUser(User user) {

        Customer customerUser = (Customer) user;
        int customerId = customerUser.getCustomerId();

        if (mockCustomerData.containsKey(customerId)) {
            mockCustomerData.get(customerId).setCustomerId(customerUser.getCustomerId());
            mockCustomerData.get(customerId).setCustomerName(customerUser.getCustomerName());
            mockCustomerData.get(customerId).setCustomerContact(customerUser.getCustomerContact());
            mockCustomerData.get(customerId).setCustomerEmail(customerUser.getCustomerEmail());

            return true;
        }
        return false;
    }

}

