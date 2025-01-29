package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerPaymentBO extends SuperBO {
    boolean save(CustomerPaymentDto dto) throws SQLException, ClassNotFoundException;
    boolean update(CustomerPaymentDto dto) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    CustomerPaymentDto search(String id) throws SQLException, ClassNotFoundException;
    ArrayList<CustomerPaymentDto> getAll() throws SQLException, ClassNotFoundException;
    String getNextId() throws SQLException, ClassNotFoundException;
    boolean saveCustomerPaymentList(ArrayList<CustomerPaymentDto> customerPaymentDtos);
}
