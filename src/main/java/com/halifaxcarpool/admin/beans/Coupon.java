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

}
