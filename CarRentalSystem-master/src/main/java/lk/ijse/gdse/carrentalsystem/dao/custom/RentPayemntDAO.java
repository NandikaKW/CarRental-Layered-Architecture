package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.dto.RentPayemntDto;
import lk.ijse.gdse.carrentalsystem.entity.RentPayemnt;

import java.sql.SQLException;

public interface RentPayemntDAO extends CrudDAO<RentPayemnt> {
    RentPayemntDto searchRentPayment(String rentId, String paymentId) throws SQLException, ClassNotFoundException;
    boolean deleteRentPayment(String rentId, String paymentId) throws SQLException, ClassNotFoundException;
}
