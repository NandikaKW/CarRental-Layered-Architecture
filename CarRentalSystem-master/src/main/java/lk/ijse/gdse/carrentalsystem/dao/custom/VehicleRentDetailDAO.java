package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.dto.VechileRentDetailDto;
import lk.ijse.gdse.carrentalsystem.entity.VechileRentDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface
VehicleRentDetailDAO extends CrudDAO<VechileRentDetail> {
   // boolean isVehicleRentUpdated(VechileRentDetailDto vechileRentDetailDto) throws SQLException, ClassNotFoundException;
   // boolean saveVehicleRentList(ArrayList<VechileRentDetailDto> vechileRentDetailDtos) throws SQLException, ClassNotFoundException;
    boolean deleteVehicleRent(String rentId, String vehicleId) throws SQLException, ClassNotFoundException;
    VechileRentDetailDto searchVehicleRent(String vehicleId, String rentId) throws SQLException, ClassNotFoundException;
   // boolean saveVehicleRent(VechileRentDetailDto vechileRentDetailDto) throws SQLException, ClassNotFoundException;
    boolean saveVehicleRentList(ArrayList<VechileRentDetail> vechileRentDetails) throws SQLException, ClassNotFoundException;
    boolean saveVehicleRent(VechileRentDetail vechileRentDetail) throws SQLException, ClassNotFoundException;



}
