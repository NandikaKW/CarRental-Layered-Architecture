package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.VechileRentDetailDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleRentDetailBO extends SuperBO {
    boolean saveVehicleRentList(ArrayList<VechileRentDetailDto> vechileRentDetailDtos) throws SQLException, ClassNotFoundException;
  //  boolean isVehicleRentUpdated(VechileRentDetailDto vechileRentDetailDto) throws SQLException, ClassNotFoundException;
    boolean deleteVehicleRent(String rentId, String vehicleId) throws SQLException, ClassNotFoundException;
    VechileRentDetailDto searchVehicleRent(String vehicleId, String rentId) throws SQLException, ClassNotFoundException;
    ArrayList<VechileRentDetailDto> getAllVehicleRentDetails() throws SQLException, ClassNotFoundException;
    String getNextRentId() throws SQLException, ClassNotFoundException;



}
