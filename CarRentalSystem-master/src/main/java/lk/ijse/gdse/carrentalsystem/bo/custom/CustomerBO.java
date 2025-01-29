package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.CustomerDto;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    boolean save(CustomerDto dto) throws SQLException, ClassNotFoundException;
    boolean update(CustomerDto dto) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    CustomerDto search(String id) throws SQLException, ClassNotFoundException;
    ArrayList<CustomerDto> getAll() throws SQLException, ClassNotFoundException;
    String getNextId() throws SQLException, ClassNotFoundException;
    String loadCurrentCustomerId() throws SQLException, ClassNotFoundException;
    boolean isNicExists(String nic) throws SQLException, ClassNotFoundException;
   // boolean saveCustomerPayment(CustomerPaymentDto customerPaymentDto) throws SQLException, ClassNotFoundException;
   boolean saveCustomerPayment(CustomerPaymentDto customerPaymentDto) throws SQLException, ClassNotFoundException;


}
