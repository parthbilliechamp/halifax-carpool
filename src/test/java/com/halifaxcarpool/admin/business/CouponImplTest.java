package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.admin.database.dao.CouponDaoMock;
import com.halifaxcarpool.admin.database.dao.dao.ICouponDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class CouponImplTest{

    private IAdminDaoFactory adminDaoFactory = new AdminDaoTestFactory();

    private ICouponDao couponDaoMock = adminDaoFactory.getCouponDao();


    @Test
    public void viewCouponsTest(){
        assert ((couponDaoMock.viewCoupons()).size() == 3);
    }
    @Test
    public void createCouponTest(){
        Coupon coupon = new Coupon(10,25,"2023-11-21");
        ICoupon couponImpl = new Coupon();
        assert (couponImpl.createCoupon(coupon,couponDaoMock));
    }

    @Test
    public void getMaximumDiscountValidTodayTest(){
        ICoupon couponImpl = new Coupon();
        assert (couponImpl.getMaximumDiscountValidToday(couponDaoMock) !=0);
    }

    @Test
    public void deleteCouponTest(){
        int couponId = 1;
        ICoupon couponImpl = new Coupon();
        assert couponImpl.deleteCoupon(couponId, couponDaoMock);
    }

}
