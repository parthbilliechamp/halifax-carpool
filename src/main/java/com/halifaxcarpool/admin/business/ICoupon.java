package com.halifaxcarpool.admin.business;
import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.admin.database.dao.dao.ICouponDao;

import java.util.List;
import java.time.LocalDate;
public interface ICoupon {

    boolean createCoupon(Coupon coupon, ICouponDao couponDao);

    List<Coupon> viewCoupons(ICouponDao couponDao);

    void deleteCoupon(int couponId, ICouponDao couponDao);
    Double getMaximumDiscountValidToday(ICouponDao couponDao);
}
