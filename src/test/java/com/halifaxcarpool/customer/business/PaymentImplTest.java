package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.Payment;
import com.halifaxcarpool.customer.database.dao.IPaymentDao;
import com.halifaxcarpool.customer.database.dao.PaymentDaoMockImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PaymentImplTest {

    private IPaymentDao paymentDaoMock = new PaymentDaoMockImpl();

    @Test
    public void insertPaymentDetailsTest(){
        Payment payment = new Payment(10,7,8,9,200,"NOT_INSTANTIATED");
        assert paymentDaoMock.insertPaymentRecord(payment);
    }

    @Test
    public void getCustomerRideHistoryTest(){
        int customerId = 1;
        assert paymentDaoMock.getCustomerRidePaymentList(customerId).size() ==1;
    }

    @Test
    public void getAmountDueTest(){
        int paymentId = 789;
        assert (paymentDaoMock.getAmountDue(paymentId) == 100);
    }

    @Test
    public void updatePaymentStatusToSuccessTest(){
        int paymentId = 123;
        assert (paymentDaoMock.changePaymentStatusSuccess(paymentId));
    }

    @Test
    public void driverUpdatePaymentStatusTest(){
        int paymentId = 456;
        assert (paymentDaoMock.driverUpdatePaymentStatus(paymentId));
    }

    @Test
    public void fetchPaymentDetails(){
        int customerId =1;
        int driverId = 1;
        int rideId = 1;
        assert (paymentDaoMock.fetchPaymentDetails(customerId, rideId, driverId) != null);

    }

}
