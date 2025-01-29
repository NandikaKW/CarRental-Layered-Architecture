package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.dao.custom.CustomerDAO;
import lk.ijse.gdse.carrentalsystem.db.DBConnection;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.Customer;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
//    private static final String CHECK_NIC_QUERY = "SELECT COUNT(*) FROM customer WHERE nic_number = ?";
//    private static final String INSERT_CUSTOMER_QUERY = "INSERT INTO customer (cust_id, name, address, email, nic_number, admin_id) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String INSERT_PAYMENT_QUERY = "INSERT INTO customerpayment VALUES (?,?,?,?)";

    private static final String REDUCE_PAYMENT_AMOUNT_QUERY = "UPDATE payment SET amount = amount - ? WHERE pay_id = ?";
    @Override
    public boolean save(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO customer (cust_id, name, address, email, nic_number, admin_id) VALUES (?, ?, ?, ?, ?, ?)", dto.getCust_id(), dto.getCust_name(), dto.getAddress(), dto.getEmail(), dto.getNic(), dto.getAdmin_id());
    }
    public boolean saveCustomerPaymentList(ArrayList<CustomerPaymentDto> customerPaymentDtos, Connection connection) throws SQLException, ClassNotFoundException {
        for (CustomerPaymentDto customerPaymentDto : customerPaymentDtos) {
            boolean isCustomerPaymentSaved = saveCustomerPayment(customerPaymentDto, connection);
            if (!isCustomerPaymentSaved) {
                return false;
            }
            boolean isPaymentUpdated = reducePaymentAmount(customerPaymentDto, connection);
            if (!isPaymentUpdated) {
                return false;
            }
        }
        return true;
    }
    public boolean saveCustomerPayment(CustomerPaymentDto customerPaymentDto, Connection connection) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_PAYMENT_QUERY)) {
            pstmt.setString(1, customerPaymentDto.getCust_id());
            pstmt.setString(2, customerPaymentDto.getPay_id());
            pstmt.setDate(3, customerPaymentDto.getPayment_date());
            pstmt.setBigDecimal(4, customerPaymentDto.getAmount());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean reducePaymentAmount(CustomerPaymentDto customerPaymentDto, Connection connection) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(REDUCE_PAYMENT_AMOUNT_QUERY)) {
            pstmt.setBigDecimal(1, customerPaymentDto.getAmount());
            pstmt.setString(2, customerPaymentDto.getPay_id());
            return pstmt.executeUpdate() > 0;
        }
    }


    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE customer SET name = ?, address = ?, email = ?, nic_number = ?, admin_id = ? WHERE cust_id = ?",
                dto.getCust_name(), dto.getAddress(), dto.getEmail(), dto.getNic(), dto.getAdmin_id(), dto.getCust_id()
        );

    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM customer WHERE cust_id = ?", Id);

    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer WHERE cust_id = ?", id);
        if (resultSet.next()) {
            return new Customer(
                    resultSet.getString("cust_id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getString("nic_number"),
                    resultSet.getString("admin_id")
            );
        }
        return null; // If the customer is not found

    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst =  CrudUtil.execute("select * from customer");
        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next()){
            customers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)

            ));
        }
        return customers;



    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT cust_id FROM customer ORDER BY cust_id DESC LIMIT 1");
        if (rst.next()) {
            String lastID = rst.getString(1);
            String substring = lastID.substring(1); // Strip the leading "C"
            int id = Integer.parseInt(substring);
            int newIndex = id + 1;
            return String.format("C%03d", newIndex); // Format the new ID as "Cxxx"
        }
        return "C001";

    }

    @Override
    public String loadCurrentCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT cust_id FROM customer ORDER BY cust_id DESC LIMIT 1");

        if (rst.next()) {
            return rst.getString("cust_id");  // Return the latest customer ID if exists
        }
        return "C001";  // Default to "C001" if no customer exists

    }
    public boolean isNicExists(String nic) throws SQLException, ClassNotFoundException {
        String checkNicQuery = "SELECT COUNT(*) FROM customer WHERE nic_number = ?";
        ResultSet resultSet = CrudUtil.execute(checkNicQuery, nic);
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0; // If count > 0, NIC already exists
        }
        return false; // NIC doesn't exist
    }
}


