package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.CustomerPayment;
import lk.ijse.gdse.carrentalsystem.entity.Payment;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentDAO extends CrudDAO<Payment> {
    ArrayList<String> getAllPaymentIDs() throws SQLException, ClassNotFoundException;
    BigDecimal getAvailablePaymentAmount(String paymentId) throws SQLException, ClassNotFoundException;
    String loadCurrentPaymentId() throws SQLException, ClassNotFoundException;
    boolean reducePaymentAmount(CustomerPayment customerPayment) throws SQLException, ClassNotFoundException;
}
