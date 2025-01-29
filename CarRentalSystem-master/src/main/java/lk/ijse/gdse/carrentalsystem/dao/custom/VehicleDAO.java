package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.dto.VechileRentDetailDto;
import lk.ijse.gdse.carrentalsystem.entity.VechileRentDetail;
import lk.ijse.gdse.carrentalsystem.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleDAO extends CrudDAO<Vehicle> {
  //  boolean reduceVehicleQuantity(VechileRentDetailDto vechileRentDetailDto) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllVehicleIds() throws SQLException, ClassNotFoundException;
    String loadNextVehicleId() throws SQLException, ClassNotFoundException;
    String loadCurrentVehicleId() throws SQLException, ClassNotFoundException;
    boolean reduceVehicleQuantity(VechileRentDetail vechileRentDetail) throws SQLException, ClassNotFoundException;


}
