package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.dto.PaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.Package;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PackageDAO extends CrudDAO<Package> {
    ArrayList<PaymentDto> getAllPayments() throws Exception;
    ArrayList<String> getAllPackageIds() throws SQLException, ClassNotFoundException;

}
