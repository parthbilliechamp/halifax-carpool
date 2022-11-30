package com.halifaxcarpool.admin.controller;
import java.util.List;
import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.admin.business.CouponImpl;
import com.halifaxcarpool.admin.business.ICoupon;
import com.halifaxcarpool.admin.database.dao.CouponDaoImpl;
import com.halifaxcarpool.admin.database.dao.ICouponDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class AdminController {

    private static final String VIEW_COUPONS_UI = "view_coupon_codes";
    @GetMapping("/admin/view_discounts")
    String viewCoupons(Model model) {
        String couponAttribute = "coupons";
        ICoupon coupon = new CouponImpl();
        ICouponDao couponDao = new CouponDaoImpl();
        List<Coupon>coupons = coupon.viewCoupons(couponDao);
        model.addAttribute(couponAttribute, coupons);
        return VIEW_COUPONS_UI;
    }

}
