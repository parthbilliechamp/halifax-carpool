package com.halifaxcarpool.customer.business.beans;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.customer.business.authentication.IUserAuthentication;
import com.halifaxcarpool.customer.database.dao.CustomerAuthenticationDaoImpl;
import com.halifaxcarpool.customer.database.dao.CustomerDaoImpl;
import com.halifaxcarpool.customer.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.customer.database.dao.IUserDao;

public class Customer extends User {

    public int customerId;
    String customerName;
    String customerContact;
    String customerEmail;
    String customerPassword;
    public Customer() {

    }

    public Customer(int customerId, String customerName, String customerContact, String customerEmail, String customerPassword) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
    }

    @Override
    public void registerUser(IUserDao userDao) {
        userDao.registerUser(this);
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

}
