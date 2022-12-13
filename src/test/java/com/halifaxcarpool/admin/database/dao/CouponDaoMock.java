package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.admin.business.beans.Coupon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CouponDaoMock implements ICouponDao {

    List<Coupon> coupons = new ArrayList<>();
    public CouponDaoMock(){
        Coupon coupon1 = new Coupon(1,12.5,"2022-12-31");
        coupons.add(coupon1);
        Coupon coupon2 = new Coupon(2,45,"2023-12-31");
        coupons.add(coupon2);
        Coupon coupon3 = new Coupon(3,34,"2024-12-31");
        coupons.add(coupon3);
    }
    @Override
    public boolean createCoupon(Coupon coupon) {

        coupons.add(coupon);
        Iterator<Coupon> itr = coupons.iterator();
        while(itr.hasNext()){
            Coupon tempCoupon = (Coupon)itr.next();
            if(tempCoupon.getCouponId() == coupon.getCouponId()){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Coupon> viewCoupons() {
        return coupons;
    }

    @Override
    public boolean deleteCoupon(int couponId) {
        Iterator<Coupon> itr = coupons.iterator();
        while(itr.hasNext()){
            Coupon tempCoupon = (Coupon)itr.next();
            if(tempCoupon.getCouponId() == couponId){
                coupons.remove(tempCoupon);
                return true;
            }
        }
        return false;
    }

    @Override
    public Double getMaximumDiscount() {

        Iterator<Coupon> itr = coupons.iterator();
        Double maximumDiscount = 0.0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while(itr.hasNext()){
            Coupon coupon = (Coupon) itr.next();
            LocalDateTime date = LocalDate.parse(coupon.getExpiry(), formatter).atStartOfDay();
            if((LocalDateTime.now().compareTo(date))<0){
                maximumDiscount = maximumDiscount> coupon.getDiscountPercentage()? maximumDiscount : coupon.getDiscountPercentage();

            }
        }
        return maximumDiscount;

    }
}
