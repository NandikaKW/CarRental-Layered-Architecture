package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.BOFactory;
import lk.ijse.gdse.carrentalsystem.bo.custom.PaymentBO;
import lk.ijse.gdse.carrentalsystem.dao.custom.CustomerPaymentDAO;
import lk.ijse.gdse.carrentalsystem.db.DBConnection;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.CustomerPayment;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerPaymentDAOImpl implements CustomerPaymentDAO {
    PaymentBO paymentBO= (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);



@Override
public boolean saveCustomerPayment(CustomerPayment customerPayment) throws SQLException, ClassNotFoundException {
    return CrudUtil.execute(
            "INSERT INTO customerpayment (cust_id, pay_id, payment_date, amount) VALUES (?, ?, ?, ?)",
            customerPayment.getCust_id(),
            customerPayment.getPay_id(),
            customerPayment.getPayment_date(),
            customerPayment.getAmount()
    );
}

    @Override
    public boolean save(CustomerPayment dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO customerpayment VALUES (?,?,?,?)", dto.getCust_id(), dto.getPay_id(), dto.getPayment_date(), dto.getAmount());

    }

    @Override
    public boolean update(CustomerPayment dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public CustomerPayment search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<CustomerPayment> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }
//    public boolean saveCustomerPaymentList(ArrayList<CustomerPaymentDto> customerPaymentDtos) throws SQLException, ClassNotFoundException {
//
//        System.out.println(customerPaymentDtos);
//        System.out.println(customerPaymentDtos.size());
//        for (CustomerPaymentDto customerPaymentDto : customerPaymentDtos) {
//            // Save individual vehicle rent detail
//            boolean isVehicleRentSaved = saveCustomerPayment(customerPaymentDto);
//            System.out.println("is Saved"+isVehicleRentSaved);
//            if (isVehicleRentSaved) {
//                return true;  // If saving fails, return false to trigger rollback
//            }
//
//            // Update vehicle quantity after saving rent detail
//        }
//        return false;
//
//    }
public boolean saveCustomerPaymentList(ArrayList<CustomerPayment> customerPayments) throws SQLException, ClassNotFoundException {

    for (CustomerPayment customerPayment : customerPayments) {
        // Save individual customer payment
        boolean isPaymentSaved = saveCustomerPayment(customerPayment);

        if (!isPaymentSaved) {
            return false; // If saving fails, return false to trigger rollback
        }

        // Add logic to update related entities (if required)
    }
    return true;
}





}
