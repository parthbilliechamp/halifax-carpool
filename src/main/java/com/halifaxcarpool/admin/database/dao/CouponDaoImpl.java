package com.halifaxcarpool.admin.database.dao;
import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.admin.database.dao.ICouponDao;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CouponDaoImpl implements ICouponDao {

    private final IDatabase database;
    private final Connection connection;
    public CouponDaoImpl() {
        database = new DatabaseImpl();
        connection = database.openDatabaseConnection();
    }

    @Override
    public boolean createCoupon(Coupon coupon) {
        return false;
    }


    @Override
    public List<Coupon> viewCoupons() {
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL view_coupon()");
            return buildCouponRequestsForm(resultSet);

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            //connection close
        }
        return new ArrayList<>();
    }

    public static List<Coupon> buildCouponRequestsForm(ResultSet resultSet) throws SQLException{
        List<Coupon> coupons = new ArrayList<Coupon>();
        while(resultSet.next()){
            int couponId = Integer.parseInt(resultSet.getString("coupon_id"));
            double discountPercentage = Double.parseDouble(resultSet.getString("discount_percentage"));
            LocalDate expiry = LocalDate.parse(resultSet.getString("expiry_date"));
            Coupon coupon = new Coupon(couponId, discountPercentage, expiry);
            coupons.add(coupon);

        }
        return coupons;
    }
}
