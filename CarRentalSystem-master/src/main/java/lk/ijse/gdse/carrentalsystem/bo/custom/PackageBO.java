package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;
import lk.ijse.gdse.carrentalsystem.dto.PackageDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PackageBO extends SuperBO {
    boolean save(PackageDto packageDto) throws SQLException;
    boolean update(PackageDto packageDto) throws SQLException;
    boolean delete(String packageId) throws SQLException;
    PackageDto search(String packageId) throws SQLException;
    ArrayList<PackageDto> getAll() throws SQLException;
    String getNextId() throws SQLException;
    ArrayList<String> getIds() throws SQLException;
    String loadCurrentPackageId() throws SQLException, ClassNotFoundException;
    ArrayList<PackageDto> getAllPackages() throws SQLException, ClassNotFoundException;


}
