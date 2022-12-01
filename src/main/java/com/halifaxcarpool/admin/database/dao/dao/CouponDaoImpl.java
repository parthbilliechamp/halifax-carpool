package com.halifaxcarpool.admin.database.dao.dao;
import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
        try{
            Statement statement = connection.createStatement();
            System.out.println("CALL insert_coupon_details(" + coupon.getCouponId()+ ","+
                    coupon.getDiscountPercentage()+ ","
                    + coupon.getExpiry() +")");
            statement.executeQuery("CALL insert_coupon_details(" + coupon.getCouponId()+ ","+
                    coupon.getDiscountPercentage()+ ","
                    + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(coupon.getExpiry()) +")");
            return true;
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try{
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();

            }
        }
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
            try {
                connection.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }


    @Override
    public boolean deleteCoupon(int couponId) {
        try{
            Statement statement = connection.createStatement();
            statement.executeQuery("CALL delete_coupon("+ couponId+ ")");
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            try{
                connection.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
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
