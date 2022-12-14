package com.halifaxcarpool.admin.database.dao;
import com.halifaxcarpool.admin.business.beans.Coupon;
import java.util.List;
public interface ICouponDao {
    boolean createCoupon(Coupon coupon);
    List<Coupon> viewCoupons();
    boolean deleteCoupon(int couponId);
    Double getMaximumDiscount();
}
