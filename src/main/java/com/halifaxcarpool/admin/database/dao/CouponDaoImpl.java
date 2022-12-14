package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CouponDaoImpl implements ICouponDao {

    private final IDatabase database;
    private Connection connection;

    public CouponDaoImpl() {
        database = new DatabaseImpl();

    }

    @Override
    public boolean createCoupon(Coupon coupon) {
        try {
            connection = database.openDatabaseConnection();
            CallableStatement statement = connection.prepareCall("CALL insert_coupon_details(?,?,?)");
            statement.setInt(1, coupon.getCouponId());
            statement.setDouble(2, coupon.getDiscountPercentage());
            statement.setDate(3, java.sql.Date.valueOf(coupon.getExpiry()));
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return false;
    }

    @Override
    public List<Coupon> viewCoupons() {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL view_coupon()");
            return buildCouponRequestsForm(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return new ArrayList<>();
    }


    @Override
    public boolean deleteCoupon(int couponId) {
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "CALL delete_coupon(?)";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, couponId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return false;
    }

    @Override
    public Double getMaximumDiscount() {
        try {
            connection = database.openDatabaseConnection();
            CallableStatement statement = connection.prepareCall("CALL get_maximum_discount()");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Double.parseDouble(resultSet.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return 0.0;
    }

    private static List<Coupon> buildCouponRequestsForm(ResultSet resultSet) throws SQLException {
        List<Coupon> coupons = new ArrayList<Coupon>();
        while (resultSet.next()) {
            String couponIdLiteral = "coupon_id";
            String discountPercentageLiteral = "discount_percentage";
            int couponId = Integer.parseInt(resultSet.getString(couponIdLiteral));
            String expiryDateLiteral = "expiry_date";
            double discountPercentage = Double.parseDouble(resultSet.getString(discountPercentageLiteral));
            String expiry = resultSet.getString(expiryDateLiteral);
            Coupon coupon = new Coupon(couponId, discountPercentage, expiry);
            coupons.add(coupon);

        }
        return coupons;
    }
}
