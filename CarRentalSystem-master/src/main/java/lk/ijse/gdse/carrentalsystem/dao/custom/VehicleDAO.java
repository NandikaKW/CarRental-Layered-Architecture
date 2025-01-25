package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.dto.VechileRentDetailDto;
import lk.ijse.gdse.carrentalsystem.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleDAO extends CrudDAO<Vehicle> {
    String loadNextPackageId() throws SQLException, ClassNotFoundException;
    boolean reduceVehicleQuantity(VechileRentDetailDto vechileRentDetailDto) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllVehicleIds() throws SQLException, ClassNotFoundException;
    String loadCurrentPackageId() throws SQLException, ClassNotFoundException;

}
