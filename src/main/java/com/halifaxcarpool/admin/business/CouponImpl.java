package com.halifaxcarpool.admin.business;
import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.admin.database.dao.ICouponDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CouponImpl implements ICoupon{
    @Override
    public boolean createCoupon(Coupon coupon) {
    return false;
    }

    @Override
    public List<Coupon> viewCoupons(ICouponDao iCouponDao) {

        return iCouponDao.viewCoupons();
    }

    @Override
    public void deleteCoupon(int couponId) {

    }

    @Override
    public Coupon updateCoupon(double discountPercentage, LocalDate expiry) {
        return null;
    }
}
