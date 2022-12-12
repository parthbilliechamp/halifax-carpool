package com.halifaxcarpool.customer.business.beans;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.driver.business.beans.Driver;

public class Customer extends User {

    public int customerId;
    String customerName;
    String customerContact;
    String customerEmail;
    String customerPassword;
    public Customer() {

    }

    public Customer(Customer.Builder builder) {
        this.customerId = builder.customerId;
        this.customerName = builder.customerName;
        this.customerContact = builder.customerContact;
        this.customerEmail = builder.customerEmail;
    }

    public Customer(int customerId, String customerName, String customerContact, String customerEmail, String customerPassword) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
    }

    @Override
    public void registerUser(IUserDao userDao) throws Exception {
        try {
            userDao.registerUser(this);
        } catch (Exception e) {
            if(e.getMessage().contains("customer_email_UNIQUE")) {
                throw new Exception("Customer already exists");
            }
            else {
                throw new Exception("Some error has occurred");
            }
        }
    }

    @Override
    public boolean updateUser(IUserDao userDao) {
        return userDao.updateUser(this);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerContact='" + customerContact + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerPassword='" + customerPassword + '\'' +
                '}';
    }

    public static class Builder {
        private int customerId;
        private String customerName;
        private String customerContact;
        private String customerEmail;

        public Customer.Builder withCustomerId(int customerId) {
            this.customerId = customerId;
            return this;
        }
        public Customer.Builder withCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }
        public Customer.Builder withCustomerContact(String customerContact) {
            this.customerContact = customerContact;
            return this;
        }
        public Customer.Builder withCustomerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
            return this;
        }
        public Customer build() {
            return new Customer(this);
        }
    }
}
