package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.Payment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PaymentDaoMockImpl implements IPaymentDao{
    List<Payment> payments = new ArrayList<>();
    public static final String  NOT_INITIATED = "NOT_INITIATED";
    public static final String  COMPLETED = "COMPLETED";
    public static final String  SUCCESSFUL = "SUCCESSFUL";
    public PaymentDaoMockImpl(){
        Payment payment1 = new Payment(123,1,1,1,80,NOT_INITIATED);
        payments.add(payment1);
        Payment payment2 = new Payment(456,2,3,4,53.33,COMPLETED);
        payments.add(payment2);
        Payment payment3 = new Payment(789,3,4,5,100,SUCCESSFUL);
        payments.add(payment3);
    }
    @Override
    public boolean insertPaymentRecord(Payment payment) {
        payments.add(payment);
        Iterator<Payment> itr = payments.iterator();
        while(itr.hasNext()){
            Payment iter = (Payment)itr.next();
            if(payment.getPaymentId() == iter.getPaymentId()){
                return true;
            }
        }
        return false;
    }



    @Override
    public List<Payment> getCustomerRidePaymentList(int customerId) {
        List<Payment> customerList = new ArrayList<>();
        Iterator<Payment> itr = payments.iterator();
        while(itr.hasNext()){
            Payment iter = (Payment)itr.next();
            if(iter.getCustomerId() == customerId){
                customerList.add(iter);
            }
        }
        return customerList;
    }

    @Override
    public double getAmountDue(int paymentId) {
        Iterator<Payment> itr = payments.iterator();
        while(itr.hasNext()){
            Payment iter = (Payment)itr.next();
            if(iter.getPaymentId() == paymentId){
                return iter.getAmountDue();
            }
        }

        return 0;
    }

    @Override
    public boolean changePaymentStatusSuccess(int paymentId) {
        Iterator<Payment> itr = payments.iterator();
        while(itr.hasNext()){
            Payment iter = (Payment)itr.next();
            if(paymentId == iter.getPaymentId()){
                iter.setStatus(SUCCESSFUL);
                if(iter.getStatus() == SUCCESSFUL)
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean driverUpdatePaymentStatus(int paymentId) {
        Iterator<Payment> itr = payments.iterator();

        while(itr.hasNext()){

            Payment iter = (Payment)itr.next();

            if(paymentId == iter.getPaymentId()){

                iter.setStatus(COMPLETED);
                if(iter.getStatus() == COMPLETED)
                    return true;
            }
        }
        return false;

    }

    @Override
    public Payment fetchPaymentDetails(int customerId, int rideId, int driverId) {

        Iterator<Payment> itr = payments.iterator();

        while(itr.hasNext()){

            Payment iter = (Payment)itr.next();

            if(iter.getCustomerId() == customerId && iter.getDriverId() == driverId && iter.getRideId() == rideId){

                return iter;
            }
        }
        return null;
    }
}
