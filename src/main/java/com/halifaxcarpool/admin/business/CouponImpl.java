package com.halifaxcarpool.admin.business;
import com.halifaxcarpool.admin.beans.Coupon;
import com.halifaxcarpool.admin.business.ICoupon;

import java.time.LocalDate;
import java.util.List;

public class CouponImpl implements ICoupon{
    @Override
    public void createCoupon(Coupon coupon) {

    }

    @Override
    public List<Coupon> viewCoupons() {
        return null;
    }

    @Override
    public void deleteCoupon(int couponId) {

    }

    @Override
    public Coupon updateCoupon(double discountPercentage, LocalDate expiry) {
        return null;
    }
}
