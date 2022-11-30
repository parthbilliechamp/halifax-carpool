package com.halifaxcarpool.admin.business.beans;

import java.time.LocalDate;
public class Coupon {
    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int couponId;
    public double discountPercentage;

    public LocalDate getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDate expiry) {
        this.expiry = expiry;
    }

    public LocalDate expiry;

    public Coupon(int couponId, double discountPercentage, LocalDate expiry){
        this.couponId = couponId;
        this.discountPercentage = discountPercentage;
        this.expiry = expiry;
    }
    @Override
    public String toString(){
        return "Coupon{" +
                "couponId="+ this.couponId+
                "discountPercentage="+this.discountPercentage+
                "expiry="+this.expiry.toString()+
                "}";
    }



}
