package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;
import lk.ijse.gdse.carrentalsystem.dto.PaymentDto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    boolean savePayment(PaymentDto dto) throws SQLException, ClassNotFoundException;
    boolean updatePayment(PaymentDto dto) throws SQLException, ClassNotFoundException;
    boolean deletePayment(String id) throws SQLException, ClassNotFoundException;
    PaymentDto searchPayment(String id) throws SQLException, ClassNotFoundException;
    ArrayList<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException;
    String getNextPaymentId() throws SQLException, ClassNotFoundException;
    BigDecimal getAvailablePaymentAmount(String paymentId) throws SQLException, ClassNotFoundException;
    String getCurrentPaymentId() throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllPaymentIds() throws SQLException, ClassNotFoundException;
    boolean reducePaymentAmount(CustomerPaymentDto customerPaymentDto) throws SQLException, ClassNotFoundException;
}
