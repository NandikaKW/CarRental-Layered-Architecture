package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.dto.PackageDto;
import lk.ijse.gdse.carrentalsystem.dto.PaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.Package;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PackageDAO extends CrudDAO<Package> {
    ArrayList<String> getAllPackageIds() throws SQLException, ClassNotFoundException;
    String loadCurrentPackageId() throws SQLException, ClassNotFoundException;
    ArrayList<PackageDto> getAllPackages() throws SQLException, ClassNotFoundException;
}
