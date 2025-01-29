package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.entity.Rent;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RentDAO extends CrudDAO<Rent> {
    boolean reserveVehicle(String vehicleId) throws SQLException, ClassNotFoundException;
    String getCurrentId() throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllRentIds() throws SQLException, ClassNotFoundException;
    String loadNextRentId() throws SQLException, ClassNotFoundException;
    String loadCurrentRentId() throws SQLException, ClassNotFoundException;
}
