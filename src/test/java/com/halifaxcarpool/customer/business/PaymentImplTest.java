package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.Payment;
import com.halifaxcarpool.customer.business.payment.IPayment;
import com.halifaxcarpool.customer.database.dao.IPaymentDao;
import com.halifaxcarpool.customer.database.dao.PaymentDaoMockImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PaymentImplTest {

    private final ICustomerDaoFactory customerDaoFactory = new CustomerDaoTestFactory();
    private final IPaymentDao paymentDaoMock = customerDaoFactory.getPaymentDao();

    @Test
    public void insertPaymentDetailsTest(){
        Payment payment = new Payment(10,7,8,9,200,"NOT_INSTANTIATED");
        assert paymentDaoMock.insertPaymentRecord(payment);
    }

    @Test
    public void getCustomerRideHistoryTest(){
        int customerId = 1;
        IPayment payment = new Payment();
        assert payment.getCustomerRideHistory(customerId,paymentDaoMock).size()==1;

    }

    @Test
    public void getAmountDueTest(){
        int paymentId = 789;
        IPayment payment = new Payment();

        assert (payment.getAmountDue(paymentId,paymentDaoMock) == 100);
    }

    @Test
    public void updatePaymentStatusToSuccessTest(){
        int paymentId = 123;
        IPayment payment = new Payment();
        assert (payment.updatePaymentStatusToSuccess(paymentId, paymentDaoMock));

    }

    @Test
    public void driverUpdatePaymentStatusTest(){
        int paymentId = 456;
        IPayment payment = new Payment();
        assert (payment.driverUpdatePaymentStatus(paymentId, paymentDaoMock));

    }

    @Test
    public void fetchPaymentDetails(){
        int customerId =1;
        int driverId = 1;
        int rideId = 1;
        IPayment payment = new Payment();
        assert (payment.fetchPaymentDetails(customerId, rideId, driverId,paymentDaoMock) != null);

    }

}
