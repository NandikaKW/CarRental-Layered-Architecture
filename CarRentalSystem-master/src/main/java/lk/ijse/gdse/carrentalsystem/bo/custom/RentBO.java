package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.RentDto;
import lk.ijse.gdse.carrentalsystem.dto.VechileRentDetailDto;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public interface RentBO extends SuperBO {
    boolean saveRent(RentDto rentDto) throws SQLException, ClassNotFoundException;
    boolean updateRent(RentDto rentDto) throws SQLException, ClassNotFoundException;
    boolean deleteRent(String rentId) throws SQLException, ClassNotFoundException;
    RentDto searchRent(String rentId) throws SQLException, ClassNotFoundException;
    ArrayList<RentDto> getAllRents() throws SQLException, ClassNotFoundException;
    String getNextRentId() throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllRentIds() throws SQLException, ClassNotFoundException;
    String loadCurrentRentId() throws SQLException;
    boolean processRentTransaction(String rentId, String startDateStr, String endDateStr, String custId, String agreementId, ArrayList<VechileRentDetailDto> vehicleRentDetailDtos) throws SQLException, ClassNotFoundException, ParseException;
}
