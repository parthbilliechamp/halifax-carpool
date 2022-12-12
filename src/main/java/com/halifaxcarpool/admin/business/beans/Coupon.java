package com.halifaxcarpool.admin.business.beans;

import java.sql.Date;
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

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String expiry;

    public Coupon(int couponId, double discountPercentage, String expiry){
        this.couponId = couponId;
        this.discountPercentage = discountPercentage;
        this.expiry = expiry;
    }

    public Coupon(){

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
