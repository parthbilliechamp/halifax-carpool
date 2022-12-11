package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.Payment;
import jdk.vm.ci.code.site.Call;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoImpl implements IPaymentDao{
    private final IDatabase database;
    private Connection connection;
    public PaymentDaoImpl(){
        this.database = new DatabaseImpl();
    }
    @Override
    public void insertPaymentRecord(Payment payment) {
        try{
            connection = database.openDatabaseConnection();
            String query = "CALL insert_payment_record(?,?,?,?,?,?)";

            CallableStatement statement = connection.prepareCall(query);
            statement.setInt(1, payment.getPaymentId());
            statement.setInt(2,payment.getRideId());
            statement.setInt(3,payment.getCustomerId());
            statement.setInt(4,payment.getDriverId());
            statement.setDouble(5, payment.getAmountDue());
            statement.setString(6,payment.getStatus());
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
            }
        }


    @Override
    public void updatePaymentStatus(int rideId, int customerId) {

    }

    @Override
    public List<Payment> getCustomerRidePaymentList(int customerId) {

        try{
            connection = database.openDatabaseConnection();
            CallableStatement statement = connection.prepareCall("CALL get_customer_ride_payment_list(?)");
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            return buildPaymentList(resultSet);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public double getAmountDue(int paymentId){
        try{
            connection = database.openDatabaseConnection();
            CallableStatement statement = connection.prepareCall("CALL get_amount_due(?)");
            statement.setInt(1,paymentId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Double amountDue = Double.parseDouble(resultSet.getString(1));
            return  amountDue;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
        return 0.0;
    }

    @Override
    public void changePaymentStatusSuccess(int paymentId) {
        try{
            connection = database.openDatabaseConnection();
            CallableStatement statement = connection.prepareCall("CALL change_payment_status_to_success(?)");
            statement.setInt(1, paymentId);
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
    }

    private static List<Payment> buildPaymentList(ResultSet resultSet) throws SQLException{
        List<Payment> payments = new ArrayList<>();
        while(resultSet.next()){
            int paymentId = Integer.parseInt(resultSet.getString("payment_id"));
            int rideId = Integer.parseInt(resultSet.getString("ride_id"));
            int customerId = Integer.parseInt(resultSet.getString("customer_id"));
            int driverId = Integer.parseInt(resultSet.getString("driver_id"));
            double amountDue = Double.parseDouble(resultSet.getString("amount_due"));
            String paymentStatus = resultSet.getString("payment_status");
            Payment payment = new Payment(paymentId, rideId, customerId, driverId, amountDue, paymentStatus);
            payments.add(payment);

        }
        return payments;
    }
}
