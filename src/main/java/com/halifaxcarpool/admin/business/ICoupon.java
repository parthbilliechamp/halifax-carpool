package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.admin.database.dao.ICouponDao;

import java.util.List;
public interface ICoupon {

    boolean createCoupon(Coupon coupon, ICouponDao couponDao);

    List<Coupon> viewCoupons(ICouponDao couponDao);

    boolean deleteCoupon(int couponId, ICouponDao couponDao);
    Double getMaximumDiscountValidToday(ICouponDao couponDao);
}
