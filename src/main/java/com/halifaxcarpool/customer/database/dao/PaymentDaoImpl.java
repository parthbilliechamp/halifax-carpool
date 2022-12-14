package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.Payment;
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
    public boolean insertPaymentRecord(Payment payment) {
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
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
            }
        return false;
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
        double amountDue = 0.0;
        try{
            connection = database.openDatabaseConnection();
            CallableStatement statement = connection.prepareCall("CALL get_amount_due(?)");
            statement.setInt(1,paymentId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            amountDue = resultSet.getDouble(1);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
        return  amountDue;
    }

    @Override
    public boolean changePaymentStatusSuccess(int paymentId) {
        try{
            connection = database.openDatabaseConnection();
            CallableStatement statement = connection.prepareCall("CALL change_payment_status_to_success(?)");
            statement.setInt(1, paymentId);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
        return false;
    }

    @Override
    public boolean driverUpdatePaymentStatus(int paymentId) {
        try{
            connection = database.openDatabaseConnection();
            CallableStatement statement = connection.prepareCall("CALL change_payment_status_to_completed(?)");
            statement.setInt(1, paymentId);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
        return false;
    }

    @Override
    public Payment fetchPaymentDetails(int customerId, int rideId, int driverId) {
        try {
            connection = database.openDatabaseConnection();
            CallableStatement statement = connection.prepareCall("CALL fetch_payment_details(?,?,?)");
            statement.setInt(1, customerId);
            statement.setInt(2,rideId);
            statement.setInt(3, driverId);
            ResultSet resultSet = statement.executeQuery();
            List<Payment> payments = buildPaymentList(resultSet);
            if(payments.size() == 1) {
                return payments.get(0);
            }
            return null;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
        return  null;
    }

    private static List<Payment> buildPaymentList(ResultSet resultSet) throws SQLException{
        List<Payment> payments = new ArrayList<>();
        while(resultSet.next()){
            String paymentIdLiteral = "payment_id";
            String rideIdLiteral = "ride_id";
            String customerIdLiteral = "customer_id";
            String driverIdLiteral = "driver_id";
            String amountDueLiteral = "amount_due";
            String paymentStatusLiteral = "payment_status";

            int paymentId = Integer.parseInt(resultSet.getString(paymentIdLiteral));
            int rideId = Integer.parseInt(resultSet.getString(rideIdLiteral));
            int customerId = Integer.parseInt(resultSet.getString(customerIdLiteral));
            int driverId = Integer.parseInt(resultSet.getString(driverIdLiteral));
            double amountDue = Double.parseDouble(resultSet.getString(amountDueLiteral));

            String paymentStatus = resultSet.getString(paymentStatusLiteral);
            Payment payment = new Payment(paymentId, rideId, customerId, driverId, amountDue, paymentStatus);
            payments.add(payment);

        }
        return payments;
    }
}
