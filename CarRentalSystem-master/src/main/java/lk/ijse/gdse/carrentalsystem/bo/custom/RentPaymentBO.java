package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.RentPayemntDto;

import java.sql.SQLException;
import java.util.List;

public interface RentPaymentBO extends SuperBO {
    boolean saveRentPayment(RentPayemntDto dto) throws SQLException, ClassNotFoundException;
    boolean updateRentPayment(RentPayemntDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteRentPayment(String rentId, String paymentId) throws SQLException, ClassNotFoundException;
    RentPayemntDto searchRentPayment(String rentId, String paymentId) throws SQLException, ClassNotFoundException;
    List<RentPayemntDto> getAllRentPayments() throws SQLException, ClassNotFoundException;
    String getCurrentRentId() throws SQLException, ClassNotFoundException;
    String getCurrentPaymentId() throws SQLException, ClassNotFoundException;
}
