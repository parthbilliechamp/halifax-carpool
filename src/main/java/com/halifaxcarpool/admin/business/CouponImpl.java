package com.halifaxcarpool.admin.business;
import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.admin.database.dao.dao.ICouponDao;

import java.time.LocalDate;
import java.util.List;

public class CouponImpl implements ICoupon {
    @Override
    public void createCoupon(Coupon coupon, ICouponDao couponDao) {
        couponDao.createCoupon(coupon);
    }

    @Override
    public List<Coupon> viewCoupons(ICouponDao iCouponDao) {

        return iCouponDao.viewCoupons();
    }

    @Override
    public void deleteCoupon(int couponId, ICouponDao couponDao) {
        couponDao.deleteCoupon(couponId);
    }

    @Override
    public Coupon updateCoupon(double discountPercentage, LocalDate expiry) {
        return null;
    }
}
