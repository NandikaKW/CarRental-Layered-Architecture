package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.dao.custom.PaymentDAO;
import lk.ijse.gdse.carrentalsystem.db.DBConnection;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.CustomerPayment;
import lk.ijse.gdse.carrentalsystem.entity.Payment;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
//    private static final String REDUCE_PAYMENT_AMOUNT_QUERY = "UPDATE payment SET amount = amount - ? WHERE pay_id = ?";

//    @Override
//    public boolean reducePaymentAmount(CustomerPaymentDto customerPaymentDto) throws SQLException, ClassNotFoundException {
//        try {
//            return CrudUtil.execute("UPDATE payment SET amount = amount - ? WHERE pay_id = ?", customerPaymentDto.getAmount(), customerPaymentDto.getPay_id());
//        } catch (SQLException e) {
//            System.out.println("Error while reducing payment amount for pay_id: " + customerPaymentDto.getPay_id());
//            e.printStackTrace();
//            throw e;
//        }
//    }
    @Override
    public boolean save(Payment dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO payment VALUES(?,?,?,?,?,?,?,?)", dto.getPay_id(), dto.getAmount(), dto.getDate(), dto.getInvoice(), dto.getMethod(), dto.getTransaction_reference(), dto.getTax(), dto.getDiscount_applied());

    }

    @Override
    public boolean update(Payment dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE payment SET pay_id=?, amount=?, date=?, invoice=?, method=?, transaction_reference=?, tax=?, discount=? WHERE pay_id=?",
                dto.getPay_id(),                // Correctly pass pay_id here for the first column in the update
                dto.getAmount(),
                dto.getDate(),
                dto.getInvoice(),
                dto.getMethod(),
                dto.getTransaction_reference(),
                dto.getTax(),
                dto.getDiscount_applied(),
                dto.getPay_id());               // Correctly pass pay_id again for the WHERE clause

    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM payment WHERE pay_id=?", Id);

    }

    @Override
    public Payment search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM payment WHERE pay_id=?", id);
        if (resultSet.next()) {
            return new Payment(
                    resultSet.getString("pay_id"),
                    resultSet.getBigDecimal("amount"),
                    resultSet.getDate("date"),
                    resultSet.getString("invoice"),
                    resultSet.getString("method"),
                    resultSet.getString("transaction_reference"),
                    resultSet.getBigDecimal("tax"),
                    resultSet.getBigDecimal("discount")

            );

        }
        return null;
    }

    @Override
    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM payment");
        ArrayList<Payment> payments = new ArrayList<>();
        while (resultSet.next()) {
           payments.add(new Payment(
                   resultSet.getString("pay_id"),
                   resultSet.getBigDecimal("amount"),
                   resultSet.getDate("date"),
                   resultSet.getString("invoice"),
                   resultSet.getString("method"),
                   resultSet.getString("transaction_reference"),
                   resultSet.getBigDecimal("tax"),
                   resultSet.getBigDecimal("discount")
           ));

        }
        return payments;

    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT pay_id FROM payment ORDER BY pay_id DESC LIMIT 1");
        if (resultSet.next()) {
            String lastID = resultSet.getString("pay_id");


            String numericPart = lastID.replaceAll("\\D", ""); // Remove all non-digit characters

            // Parse the numeric part to an integer
            int id = Integer.parseInt(numericPart);

            // Increment the ID
            int newId = id + 1;

            // Format the new ID, keeping the prefix "PAY"
            String newID = String.format("PAY%03d", newId);
            return newID; // Return the newly generated ID
        }
        return "PAY001";
    }



    @Override
    public ArrayList<String> getAllPaymentIDs() throws SQLException, ClassNotFoundException {
        // Execute SQL query to get all item IDs
        ResultSet rst = CrudUtil.execute("select pay_id from payment");

        // Create an ArrayList to store the item IDs
        ArrayList<String> paymentIds = new ArrayList<>();

        // Iterate through the result set and add each item ID to the list
        while (rst.next()) {
            paymentIds.add(rst.getString(1));
        }

        // Return the list of item IDs
        return paymentIds;

    }

//    @Override
//    public boolean reducePaymentAmount(CustomerPaymentDto customerPaymentDto) throws SQLException, ClassNotFoundException {
//        try {
//            return  CrudUtil.execute("UPDATE payment SET amount = amount - ? WHERE pay_id = ?",customerPaymentDto.getAmount(),customerPaymentDto.getPay_id());
//        } catch (SQLException e) {
//            System.out.println("Error while reducing payment amount for pay_id: " + customerPaymentDto.getPay_id());
//            e.printStackTrace();
//            return false;
//        } catch (ClassNotFoundException e) {
//            System.out.println("Database driver not found.");
//            e.printStackTrace();
//            return false;
//        }
//
//    }

    @Override
    public BigDecimal getAvailablePaymentAmount(String paymentId) throws SQLException, ClassNotFoundException {
        String query = "SELECT amount FROM payment WHERE pay_id = ?";
        ResultSet rs = CrudUtil.execute(query, paymentId);

        if (rs.next()) {
            return rs.getBigDecimal("amount");
        } else {
            throw new SQLException("Payment ID not found: " + paymentId);
        }
    }

    @Override
    public String loadCurrentPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT pay_id FROM payment ORDER BY pay_id DESC LIMIT 1");

        if (resultSet.next()) {
            return resultSet.getString("pay_id");
        }

        return null;

    }
    @Override
    public boolean reducePaymentAmount(CustomerPayment customerPayment) throws SQLException, ClassNotFoundException {
        try {
            // Use the CustomerPayment entity directly
            return CrudUtil.execute(
                    "UPDATE payment SET amount = amount - ? WHERE pay_id = ?",
                    customerPayment.getAmount(),
                    customerPayment.getPay_id()
            );
        } catch (SQLException e) {
            System.out.println("Error while reducing payment amount for pay_id: " + customerPayment.getPay_id());
            e.printStackTrace();
            throw e;
        }
    }



}
