package com.halifaxcarpool.admin.business.beans;

import com.halifaxcarpool.admin.business.ICoupon;
import com.halifaxcarpool.admin.database.dao.ICouponDao;

import java.util.List;

public class Coupon implements ICoupon {

    private int couponId;
    private double discountPercentage;
    private String expiry;

    public Coupon() {

    }

    public Coupon(int couponId, double discountPercentage, String expiry){
        this.couponId = couponId;
        this.discountPercentage = discountPercentage;
        this.expiry = expiry;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public int getCouponId() {
        return couponId;
    }

    public String getExpiry() {
        return expiry;
    }
    @Override
    public boolean createCoupon(Coupon coupon, ICouponDao couponDao) {
        return couponDao.createCoupon(coupon);
    }

    @Override
    public List<Coupon> viewCoupons(ICouponDao iCouponDao) {
        return iCouponDao.viewCoupons();
    }

    @Override
    public boolean deleteCoupon(int couponId, ICouponDao couponDao) {
        return couponDao.deleteCoupon(couponId);
    }

    @Override
    public Double getMaximumDiscountValidToday(ICouponDao couponDao) {
        return couponDao.getMaximumDiscount();
    }

    @Override
    public String toString(){
        return "Coupon{" +
                "couponId="+ this.couponId+
                "discountPercentage="+this.discountPercentage+
                "expiry="+ this.expiry +
                "}";
    }

}
