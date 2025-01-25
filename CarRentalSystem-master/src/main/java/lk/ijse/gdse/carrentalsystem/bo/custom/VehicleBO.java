package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.VehicleDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleBO extends SuperBO {
    boolean saveVehicle(VehicleDto dto) throws SQLException, ClassNotFoundException;
    boolean updateVehicle(VehicleDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteVehicle(String vehicleId) throws SQLException, ClassNotFoundException;
    VehicleDto searchVehicle(String vehicleId) throws SQLException, ClassNotFoundException;
    ArrayList<VehicleDto> getAllVehicles() throws SQLException, ClassNotFoundException;
    String getNextVehicleId() throws SQLException, ClassNotFoundException;
    String loadNextPackageId() throws SQLException, ClassNotFoundException;
    boolean reduceVehicleQuantity(VehicleDto vehicleDto) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllVehicleIds() throws SQLException, ClassNotFoundException;
    String loadCurrentPackageId() throws SQLException, ClassNotFoundException;

}
