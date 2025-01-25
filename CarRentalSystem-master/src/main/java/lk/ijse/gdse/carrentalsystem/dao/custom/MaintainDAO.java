package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.dto.PaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.Maintain;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MaintainDAO extends CrudDAO<Maintain>{
    String loadNextVehicleId() throws SQLException, ClassNotFoundException;
    String loadCurrentVehicleId() throws SQLException, ClassNotFoundException;

}
