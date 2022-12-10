package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.Payment;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
    public List<Payment> getRidePaymentList(int ride) {
        return null;
    }
}
