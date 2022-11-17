package com.halifaxcarpool.admin.business;
import com.halifaxcarpool.admin.beans.Coupon;
import java.util.List;
import java.time.LocalDate;
public interface ICoupon {

    void createCoupon(Coupon coupon);

    List<Coupon> viewCoupons();

    void deleteCoupon(int couponId);

    Coupon updateCoupon(double discountPercentage, LocalDate expiry);

}
