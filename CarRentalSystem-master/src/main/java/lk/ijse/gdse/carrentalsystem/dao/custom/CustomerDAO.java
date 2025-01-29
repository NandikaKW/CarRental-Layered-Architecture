package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer> {
    String loadCurrentCustomerId() throws SQLException, ClassNotFoundException;
    boolean isNicExists(String nic) throws SQLException, ClassNotFoundException;
    boolean reducePaymentAmount(CustomerPaymentDto customerPaymentDto, Connection connection) throws SQLException;
    boolean saveCustomerPayment(CustomerPaymentDto customerPaymentDto, Connection connection) throws SQLException;
    boolean saveCustomerPaymentList(ArrayList<CustomerPaymentDto> customerPaymentDtos, Connection connection) throws SQLException, ClassNotFoundException;


}
