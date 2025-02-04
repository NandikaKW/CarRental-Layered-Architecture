package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.CustomerPayment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerPaymentDAO extends CrudDAO<CustomerPayment> {
    boolean saveCustomerPaymentList(ArrayList<CustomerPayment> customerPayments) throws SQLException, ClassNotFoundException;
    boolean saveCustomerPayment(CustomerPayment customerPayment) throws SQLException, ClassNotFoundException;

}
