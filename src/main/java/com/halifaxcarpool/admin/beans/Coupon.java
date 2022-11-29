package com.halifaxcarpool.admin.beans;

import java.time.LocalDate;
public class Coupon {
    public int couponId;
    public double discountPercentage;
    public LocalDate expiry;

    public Coupon(double discountPercentage, LocalDate expiry){
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
